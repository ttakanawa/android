package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.*
import com.toggl.architecture.core.Effect.Companion.empty
import com.toggl.common.identity
import com.toggl.data.IDataSource
import com.toggl.timer.domain.effects.startTimeEntryEffect
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.states.TimeEntriesLogState
import com.toggl.timer.domain.states.TimerState

internal val globalTimerReducer = Reducer<TimerState, TimerAction, IDataSource> { state, action, dataSource ->

    when(action) {
        TimeEntriesLogAction.StartTimeEntryButtonTapped -> startTimeEntryEffect(dataSource)
        is TimeEntriesLogAction.ContinueButtonTapped -> empty()
        is TimerAction.TimeEntryUpdated -> {
            val newTimeEntries = state.value.timeEntries.map {
                if (it.id != action.id) it else action.timeEntry
            }

            state.value = state.value.copy(timeEntries = newTimeEntries)

            empty()
        }
        is TimerAction.TimeEntryCreated -> {
                state.value = state.value.copy(timeEntries = state.value.timeEntries + action.timeEntry)
            empty()
        }
    }
}

val timerReducer =
    combine (
        globalTimerReducer,
        pullback<TimeEntriesLogState, TimerState, TimeEntriesLogAction, TimerAction, Any, IDataSource>(
            reducer = timeEntriesLogReducer,
            mapToLocalState = TimeEntriesLogState.Companion::fromTimerState,
            mapToLocalAction = { it as? TimeEntriesLogAction },
            mapToLocalEnvironment = ::identity,
            mapToGlobalAction = ::identity,
            mapToGlobalState = { global: TimerState, _ -> global }
        )
    )
