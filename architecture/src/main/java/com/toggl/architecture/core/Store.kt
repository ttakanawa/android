package com.toggl.architecture.core

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.toggl.architecture.BuildConfig
import com.toggl.architecture.extensions.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.launch

abstract class StoreViewModel <Action, State : MvRxState>(
    initialState: State,
    private val reducer: SimpleReducer<State, Action>
) : BaseMvRxViewModel<State>(initialState, debugMode = BuildConfig.DEBUG) {

    @UseExperimental(InternalCoroutinesApi::class)
    fun dispatch(action: Action) {
        withState { state ->
            val effect = reducer.reduce(state, this::setState, action)
            viewModelScope.launch {
                effect.collect { action -> dispatch(action)}
            }
        }
    }
}
//
//class Store<State, Action> private constructor(
//    val state: Observable<State>,
//    val dispatch: (Action) -> Unit
//) {
//    fun <ViewState, ViewAction> view(
//        mapToLocalState: (State) -> ViewState,
//        mapToGlobalAction: (ViewAction) -> Action?
//    ) : Store<ViewState, ViewAction> {
//        return Store(
//            state = state.map(mapToLocalState),
//            dispatch = { action ->
//                val globalAction = mapToGlobalAction(action) ?: return@Store
//                dispatch(globalAction)
//            }
//        )
//    }
//
//    companion object {
//        fun <State, Action, Environment> create(
//            initialState: State,
//            reducer: Reducer<State, Action, Environment>,
//            environment: Environment
//        ): Store<State, Action> {
//
//            val disposeBag = CompositeDisposable()
//            val stateSubject = BehaviorSubject.create<State>()
//            stateSubject.onNext(initialState)
//            val state = stateSubject
//                .publish()
//                .refCount()
//                .observeOn(AndroidSchedulers.mainThread())
//            val settableValue = SettableValue(stateSubject::getValue, stateSubject::onNext)
//
//            lateinit var dispatch : (Action) -> Unit
//            dispatch = { action ->
//
//                val effect = reducer.reduce(settableValue, action, environment)
//
//                effect
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(dispatch)
//                    .addTo(disposeBag)
//            }
//
//             return Store(state, dispatch)
//        }
//    }
//}