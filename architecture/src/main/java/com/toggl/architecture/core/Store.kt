package com.toggl.architecture.core

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

class Store<State, Action> private constructor(
    val state: Flow<State>,
    val dispatch: (Action) -> Unit
) {
    @ExperimentalCoroutinesApi
    fun <ViewState, ViewAction> view(
        mapToLocalState: (State) -> ViewState,
        mapToGlobalAction: (ViewAction) -> Action?
    ) : Store<ViewState, ViewAction> {
        return Store(
            state = state.map { mapToLocalState(it) }.distinctUntilChanged(),
            dispatch = { action ->
                val globalAction = mapToGlobalAction(action) ?: return@Store
                dispatch(globalAction)
            }
        )
    }

    companion object {
        @FlowPreview
        @ExperimentalCoroutinesApi
        fun <State, Action, Environment> create(
            initialState: State,
            reducer: Reducer<State, Action, Environment>,
            environment: Environment
        ): Store<State, Action> {

            val stateChannel = ConflatedBroadcastChannel<State>()
            GlobalScope.launch {
                stateChannel.send(initialState)
            }

            val state = stateChannel
                .asFlow()
                .flowOn(Dispatchers.Main)

            val settableValue = SettableValue(stateChannel::value) { stateChannel.offer(it) }

            lateinit var dispatch : (Action) -> Unit
            dispatch = { action ->
                GlobalScope.launch {
                    reducer
                        .reduce(settableValue, action, environment)
                        .onEach { dispatch(it) }
                        .launchIn(this)
                }
            }

             return Store(state, dispatch)
        }
    }
}