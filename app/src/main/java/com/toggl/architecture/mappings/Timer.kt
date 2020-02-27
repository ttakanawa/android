package com.toggl.architecture.mappings

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.pullback
import com.toggl.environment.AppEnvironment
import com.toggl.timer.domain.reducers.timerReducer
import com.toggl.timer.domain.states.TimerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val globalTimerReducer: Reducer<AppState, AppAction, AppEnvironment> =
    pullback(
        reducer = timerReducer,
        mapToLocalState = {
            TimerState(it.timeEntries, it.editedDescription)
        },
        mapToLocalAction = {if (it is AppAction.Timer) it.timer else null },
        mapToLocalEnvironment = { it.dataSource },
        mapToGlobalAction = { AppAction.Timer(it) },
        mapToGlobalState = { global: AppState, local ->
            global.copy(timeEntries = local.timeEntries, editedDescription = local.editedDescription)
        }
    )