package com.toggl.architecture.core

import io.reactivex.rxjava3.core.Observable

interface IStore<State, Action, Environment> {
    val state: Observable<State>
    val environment: Environment
    fun dispatch(action: Action)
}

fun <GlobalState, LocalState, GlobalAction, LocalAction, GlobalEnvironment, LocalEnvironment>
        IStore<GlobalState, GlobalAction, GlobalEnvironment>.view(
    toLocalState:(GlobalState) -> LocalState,
    toGlobalAction: (LocalAction) -> GlobalAction?,
    toLocalEnvironment: (GlobalEnvironment) -> LocalEnvironment
) : IStore<LocalState, LocalAction, LocalEnvironment> {

    return StoreView<LocalState, LocalAction,LocalEnvironment>(
        actionHandler = { newAction: LocalAction ->
            val oldAction = toGlobalAction(newAction) ?: return@StoreView
            dispatch(oldAction)
        },
        state = state.map { toLocalState(it) },
        environment = toLocalEnvironment(environment))
}