
package com.toggl.architecture.core

import com.toggl.architecture.extensions.mergeAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.map

typealias ReduceFunction<State, Action> =
            (SettableValue<State>, Action) -> Effect<Action>

class Reducer<State, Action>(
    val reduce: ReduceFunction<State, Action>
)

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun <State, Action> combine(vararg reducers: Reducer<State, Action>):
        Reducer<State, Action> =
    Reducer { state, action ->
        val effects = reducers.map { it.reduce(state, action) }
        effects.mergeAll()
    }

fun <LocalState, GlobalState, LocalAction, GlobalAction>
    Reducer<LocalState, LocalAction>.pullback(
        mapToLocalState: (GlobalState) -> LocalState,
        mapToLocalAction: (GlobalAction) -> LocalAction?,
        mapToGlobalAction: (LocalAction) -> GlobalAction,
        mapToGlobalState: (GlobalState, LocalState) -> GlobalState
    ): Reducer<GlobalState, GlobalAction> =
    Reducer { globalState, globalAction ->
        val localAction = mapToLocalAction(globalAction)
            ?: return@Reducer noEffect()
        reduce(globalState.map(mapToLocalState, mapToGlobalState), localAction)
            .map { mapToGlobalAction(it) }
    }
