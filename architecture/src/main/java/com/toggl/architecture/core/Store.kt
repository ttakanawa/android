package com.toggl.architecture.core

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

class Store<State, Action> private constructor(
    val state: Flow<State>,
    val dispatch: (Action) -> Unit
) {
    fun <ViewState, ViewAction> view(
        mapToLocalState: (State) -> ViewState,
        mapToGlobalAction: (ViewAction) -> Action?
    ) : Store<ViewState, ViewAction> {
        return Store(
            state = state.map { mapToLocalState(it) },
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

            val settableValue = SettableValue(stateChannel::value) {
                val offered = stateChannel.offer(it)
                Log.d("Offering", offered.toString())
            }

            lateinit var dispatch : (Action) -> Unit
            dispatch = { action ->
                GlobalScope.launch {
                    val effect = reducer.reduce(settableValue, action, environment)
                    effect.collect {
                        dispatch(it)
                    }
                }
            }

             return Store(state, dispatch)
        }
    }
}