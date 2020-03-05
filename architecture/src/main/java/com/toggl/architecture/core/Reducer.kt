package com.toggl.architecture.core

import com.toggl.architecture.extensions.mergeAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.map

typealias ReduceFunction<State, Action, Environment> =
            (SettableValue<State>, Action, Environment) -> Effect<Action>

class Reducer<State, Action, Environment>(
    val reduce: ReduceFunction<State, Action, Environment>
)

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun <State, Action, Environment> combine(vararg reducers: Reducer<State, Action, Environment>)
        : Reducer<State, Action, Environment> =
    Reducer { state, action, environment ->
        val effects = reducers.map { it.reduce(state, action, environment) }
        effects.mergeAll()
    }

fun <LocalState, GlobalState, LocalAction, GlobalAction, Environment>
    Reducer<LocalState, LocalAction, Environment>.pullback(
    mapToLocalState: (GlobalState) -> LocalState,
    mapToLocalAction: (GlobalAction) -> LocalAction?,
    mapToGlobalState: (GlobalState, LocalState) -> GlobalState,
    mapToGlobalAction: (LocalAction) -> GlobalAction
) : Reducer<GlobalState, GlobalAction, Environment> =
    pullback(
        mapToLocalState = mapToLocalState,
        mapToLocalAction = mapToLocalAction,
        mapToLocalEnvironment =  { it },
        mapToGlobalAction = mapToGlobalAction,
        mapToGlobalState = mapToGlobalState
    )


fun <LocalState, GlobalState, LocalAction, GlobalAction, LocalEnvironment, GlobalEnvironment>
    Reducer<LocalState, LocalAction, LocalEnvironment>.pullback(
    mapToLocalState: (GlobalState) -> LocalState,
    mapToLocalAction: (GlobalAction) -> LocalAction?,
    mapToLocalEnvironment: (GlobalEnvironment) -> LocalEnvironment,
    mapToGlobalAction: (LocalAction) -> GlobalAction,
    mapToGlobalState: (GlobalState, LocalState) -> GlobalState
) : Reducer<GlobalState, GlobalAction, GlobalEnvironment> =
    Reducer { globalState, globalAction, globalEnvironment ->
        val localAction = mapToLocalAction(globalAction)
            ?: return@Reducer noEffect()
        val localEnvironment = mapToLocalEnvironment(globalEnvironment)
        reduce(globalState.map(mapToLocalState, mapToGlobalState), localAction, localEnvironment)
            .map { mapToGlobalAction(it) }
    }
