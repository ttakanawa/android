package com.toggl.architecture.mappings

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.pullback
import com.toggl.data.IDataSource
import com.toggl.environment.AppEnvironment
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.reducers.timerReducer
import com.toggl.timer.domain.states.TimerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

fun mapAppStateToTimerState(appState: AppState): TimerState =
    TimerState(appState.timeEntries, appState.timerLocalState)

fun mapAppActionToTimerAction(appAction: AppAction): TimerAction? =
    if (appAction is AppAction.Timer) appAction.timer else null

fun mapAppEnvironmentToTimerEnvironment(appEnvironment: AppEnvironment): IDataSource =
    appEnvironment.dataSource

fun mapTimerStateToAppState(appState: AppState, timerState: TimerState): AppState =
    appState.copy(timeEntries = timerState.timeEntries, timerLocalState = timerState.localState)

fun mapTimerActionToAppAction(timerAction: TimerAction): AppAction =
    AppAction.Timer(timerAction)

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val globalTimerReducer: Reducer<AppState, AppAction, AppEnvironment> =
    pullback(
        reducer = timerReducer,
        mapToLocalState = ::mapAppStateToTimerState,
        mapToLocalAction = ::mapAppActionToTimerAction,
        mapToLocalEnvironment = ::mapAppEnvironmentToTimerEnvironment,
        mapToGlobalAction = ::mapTimerActionToAppAction,
        mapToGlobalState = ::mapTimerStateToAppState
    )