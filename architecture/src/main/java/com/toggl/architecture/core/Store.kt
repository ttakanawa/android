package com.toggl.architecture.core

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

interface Store<State, Action> {
    val state: Flow<State>
    val dispatch: (Action) -> Unit
    @ExperimentalCoroutinesApi
    fun <ViewState, ViewAction> view(
        mapToLocalState: (State) -> ViewState,
        mapToGlobalAction: (ViewAction) -> Action?
    ) : Store<ViewState, ViewAction>
}

class FlowStore<State, Action> private constructor(
    override val state: Flow<State>,
    override val dispatch: (Action) -> Unit
) : Store<State, Action> {
    @ExperimentalCoroutinesApi
    override fun <ViewState, ViewAction> view(
        mapToLocalState: (State) -> ViewState,
        mapToGlobalAction: (ViewAction) -> Action?
    ) : Store<ViewState, ViewAction> {
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

            lateinit var dispatch : (Action) -> Unit
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