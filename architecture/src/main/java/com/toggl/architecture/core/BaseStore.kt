package com.toggl.architecture.core

import com.toggl.architecture.extensions.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class BaseStore<State, Action, Environment>(
    initialState: State,
    private val reducer: Reducer<State, Action, Environment>,
    override val environment: Environment
) : IStore<State, Action, Environment>
{
    private val disposeBag = CompositeDisposable()
    private val stateSubject: BehaviorSubject<State> = {
        val state = BehaviorSubject.create<State>()
        state.onNext(initialState)
        state
    }()

    override val state: Observable<State> = stateSubject
        .observeOn(AndroidSchedulers.mainThread())

    private val settableValue = SettableValue(stateSubject::getValue, stateSubject::onNext)

    override fun dispatch(action: Action) {
        // TODO Check or switch to Main Thread

        val effect = reducer.reduce(settableValue, action, environment)

        effect
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::dispatch)
            .addTo(disposeBag)
    }
}