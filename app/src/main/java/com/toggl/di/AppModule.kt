package com.toggl.di

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Store
import com.toggl.architecture.core.FlowStore
import com.toggl.architecture.core.combine
import com.toggl.architecture.mappings.*
import com.toggl.architecture.reducers.actionLoggingReducer
import com.toggl.architecture.reducers.appReducer
import com.toggl.environment.AppEnvironment
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.states.TimerState
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Module
class AppModule {

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Provides
    @Singleton
    fun appStore(environment: AppEnvironment): Store<AppState, AppAction> {

        val combinedReducers = combine(
            actionLoggingReducer,
            appReducer,
            globalTimerReducer,
            globalOnboardingReducer
        )

        return FlowStore.create(
            initialState = AppState(),
            reducer = combinedReducers,
            environment = environment
        )
    }

    @Provides
    @ExperimentalCoroutinesApi
    fun onboardingStore(store: Store<AppState, AppAction>): Store<OnboardingState, OnboardingAction> =
        store.view(
            mapToLocalState = ::mapAppStateToOnboardingState,
            mapToGlobalAction = ::mapOnboardingActionToAppAction
        )

    @Provides
    @ExperimentalCoroutinesApi
    fun timerStore(store: Store<AppState, AppAction>): Store<TimerState, TimerAction> =
        store.view(
            mapToLocalState = ::mapAppStateToTimerState,
            mapToGlobalAction = ::mapTimerActionToAppAction
        )
}