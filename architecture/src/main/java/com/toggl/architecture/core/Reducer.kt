package com.toggl.architecture.core

import kotlinx.coroutines.flow.emptyFlow

interface SimpleReducer<State, Action> {
    fun reduce(state: State, setState: ((State.() -> State) -> Unit), action: Action): Effect<Action>
    fun <Action> Unit.withoutEffect(): Effect<Action> = emptyFlow()
    infix fun <Action> Unit.withEffect(effect: Effect<Action>): Effect<Action> = effect
}

//typealias ReduceFunction<State, Action, Environment> =
//            (SettableValue<State>, Action, Environment) -> Effect<Action>
//
//class Reducer<State, Action, Environment>(
//    val reduce: ReduceFunction<State, Action, Environment>
//)
//
//fun <State, Action, Environment> combine(vararg reducers: Reducer<State, Action, Environment>)
//        : Reducer<State, Action, Environment> =
//    Reducer { state, action, environment ->
//        val effects = reducers.map { it.reduce(state, action, environment) }
//        Effect.from(effects)
//    }
//
//fun <LocalState, GlobalState, LocalAction, GlobalAction, LocalEnvironment, GlobalEnvironment> pullback(
//    reducer: Reducer<LocalState, LocalAction, LocalEnvironment>,
//    mapToLocalState: (GlobalState) -> LocalState,
//    mapToLocalAction: (GlobalAction) -> LocalAction?,
//    mapToLocalEnvironment: (GlobalEnvironment) -> LocalEnvironment,
//    mapToGlobalAction: (LocalAction) -> GlobalAction,
//    mapToGlobalState: (GlobalState, LocalState) -> GlobalState
//) : Reducer<GlobalState, GlobalAction, GlobalEnvironment> =
//    Reducer { globalState, globalAction, globalEnvironment ->
//        val localAction = mapToLocalAction(globalAction)
//            ?: return@Reducer Effect.empty()
//        val localEnvironment = mapToLocalEnvironment(globalEnvironment)
//        reducer
//            .reduce(globalState.map(mapToLocalState, mapToGlobalState), localAction, localEnvironment)
//            .map(mapToGlobalAction)
//            .toEffect()
//    }
