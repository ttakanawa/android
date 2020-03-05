package com.toggl.architecture.mappings

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.repository.Repository
import com.toggl.timer.common.domain.TimerAction
import com.toggl.timer.common.domain.TimerState

fun mapAppStateToTimerState(appState: AppState): TimerState =
    TimerState(
        appState.timeEntries,
        appState.timerLocalState
    )

fun mapAppActionToTimerAction(appAction: AppAction): TimerAction? =
    if (appAction is AppAction.Timer) appAction.timer else null
fun mapTimerStateToAppState(appState: AppState, timerState: TimerState): AppState =
    appState.copy(timeEntries = timerState.timeEntries, timerLocalState = timerState.localState)

fun mapTimerActionToAppAction(timerAction: TimerAction): AppAction =
    AppAction.Timer(timerAction)
