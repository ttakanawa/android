package com.toggl.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.combine
import com.toggl.architecture.core.pullback
import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.domain.mappings.mapAppActionToOnboardingAction
import com.toggl.domain.mappings.mapAppActionToTimerAction
import com.toggl.domain.mappings.mapAppStateToOnboardingState
import com.toggl.domain.mappings.mapAppStateToTimerState
import com.toggl.domain.mappings.mapOnboardingActionToAppAction
import com.toggl.domain.mappings.mapOnboardingStateToAppState
import com.toggl.domain.mappings.mapTimerActionToAppAction
import com.toggl.domain.mappings.mapTimerStateToAppState
import com.toggl.environment.services.analytics.AnalyticsService
import com.toggl.onboarding.domain.reducers.OnboardingReducer
import com.toggl.timer.common.domain.TimerReducer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

typealias AppReducer = Reducer<AppState, AppAction>

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun createAppReducer(
    timeEntryListReducer: TimeEntryListReducer,
    onboardingReducer: OnboardingReducer,
    timerReducer: TimerReducer,
    analyticsService: AnalyticsService
): AppReducer =
    combine(
        timeEntryListReducer,
        createLoggingReducer(),
        createAnalyticsReducer(analyticsService),
        timerReducer.pullback(
            mapToLocalState = ::mapAppStateToTimerState,
            mapToLocalAction = ::mapAppActionToTimerAction,
            mapToGlobalState = ::mapTimerStateToAppState,
            mapToGlobalAction = ::mapTimerActionToAppAction
        ),
        onboardingReducer.pullback(
            mapToLocalState = ::mapAppStateToOnboardingState,
            mapToLocalAction = ::mapAppActionToOnboardingAction,
            mapToGlobalState = ::mapOnboardingStateToAppState,
            mapToGlobalAction = ::mapOnboardingActionToAppAction
        )
    )
