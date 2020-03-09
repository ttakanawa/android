/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2020, Toggl
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * © 2020 GitHub, Inc.
 */
package com.toggl.architecture.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface Store<State, Action> {
    val state: Flow<State>
    val dispatch: (Action) -> Unit
    @ExperimentalCoroutinesApi
    fun <ViewState, ViewAction> view(
        mapToLocalState: (State) -> ViewState,
        mapToGlobalAction: (ViewAction) -> Action?
    ): Store<ViewState, ViewAction>
}

class FlowStore<State, Action> private constructor(
    override val state: Flow<State>,
    override val dispatch: (Action) -> Unit
) : Store<State, Action> {
    @ExperimentalCoroutinesApi
    override fun <ViewState, ViewAction> view(
        mapToLocalState: (State) -> ViewState,
        mapToGlobalAction: (ViewAction) -> Action?
    ): Store<ViewState, ViewAction> {
        return FlowStore(
            state = state.map { mapToLocalState(it) }.distinctUntilChanged(),
            dispatch = { action ->
                val globalAction = mapToGlobalAction(action) ?: return@FlowStore
                dispatch(globalAction)
            }
        )
    }

    companion object {
        @FlowPreview
        @ExperimentalCoroutinesApi
        fun <State, Action> create(
            initialState: State,
            reducer: Reducer<State, Action>
        ): Store<State, Action> {

            val stateChannel = ConflatedBroadcastChannel<State>()
            GlobalScope.launch {
                stateChannel.send(initialState)
            }

            val state = stateChannel
                .asFlow()
                .flowOn(Dispatchers.Main)

            val settableValue = SettableValue(stateChannel::value) { stateChannel.offer(it) }

            lateinit var dispatch: (Action) -> Unit
            dispatch = { action ->
                GlobalScope.launch {
                    reducer
                        .reduce(settableValue, action)
                        .onEach { dispatch(it) }
                        .launchIn(this)
                }
            }

            return FlowStore(state, dispatch)
        }
    }
}
