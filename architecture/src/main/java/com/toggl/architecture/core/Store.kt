package com.toggl.architecture.core

import io.reactivex.rxjava3.core.Observable

typealias Store<State, Action, Environment> = StoreView<State, Action, Environment>

class StoreView<State, Action, Environment>(
    private val actionHandler: (Action) -> Unit,
    override val state: Observable<State>,
    override val environment: Environment
): IStore<State, Action, Environment> {

    constructor(IStore: IStore<State, Action, Environment>) :
            this(IStore::dispatch, IStore.state, IStore.environment)

    override fun dispatch(action: Action) {
        actionHandler(action)
    }

    companion object {
        fun <State, Action, Environment> create(
            initialState: State,
            reducer: Reducer<State, Action, Environment>,
            environment: Environment
        ): Store<State, Action, Environment> =
            StoreView(
                BaseStore(
                    initialState,
                    reducer,
                    environment
                )
            )
    }
}

