package com.toggl.architecture.reducers

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.combine
import com.toggl.architecture.core.pullback
import com.toggl.architecture.mappings.*
import com.toggl.environment.AppEnvironment
import com.toggl.onboarding.domain.reducers.OnboardingReducer
import com.toggl.timer.common.domain.TimerReducer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

typealias AppReducer = Reducer<AppState, AppAction, AppEnvironment>

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun createAppReducer(
    onboardingReducer: OnboardingReducer,
    timerReducer: TimerReducer
) : AppReducer =
    combine(
        createLoggingReducer(),
        timerReducer.pullback(
            mapToLocalState = ::mapAppStateToTimerState,
            mapToLocalAction = ::mapAppActionToTimerAction,
            mapToLocalEnvironment = ::mapAppEnvironmentToTimerEnvironment,
            mapToGlobalAction = ::mapTimerActionToAppAction,
            mapToGlobalState = ::mapTimerStateToAppState
        ),
        onboardingReducer.pullback(
            mapToLocalState = ::mapAppStateToOnboardingState,
            mapToLocalAction = ::mapAppActionToOnboardingAction,
            mapToLocalEnvironment = ::mapAppEnvironmentToOnboardingEnvironment,
            mapToGlobalAction = ::mapOnboardingActionToAppAction,
            mapToGlobalState = ::mapOnboardingStateToAppState
        )
    )