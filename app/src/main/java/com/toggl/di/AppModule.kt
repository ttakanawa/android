package com.toggl.di

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.*
import com.toggl.architecture.mappings.*
import com.toggl.architecture.reducers.AppReducer
import com.toggl.architecture.reducers.createAppReducer
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.reducers.OnboardingReducer
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.timer.common.domain.TimerAction
import com.toggl.timer.common.domain.TimerReducer
import com.toggl.timer.common.domain.TimerState
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    fun appReducer(
        onboardingReducer: OnboardingReducer,
        timerReducer: TimerReducer) =
        createAppReducer(onboardingReducer, timerReducer)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Provides
    @Singleton
    fun appStore(appReducer: AppReducer): Store<AppState, AppAction> {
        return FlowStore.create(
            initialState = AppState(),
            reducer = appReducer
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