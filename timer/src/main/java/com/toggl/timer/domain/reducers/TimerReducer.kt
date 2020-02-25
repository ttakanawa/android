package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.*
import com.toggl.architecture.core.Effect.Companion.empty
import com.toggl.common.identity
import com.toggl.data.IDataSource
import com.toggl.models.domain.TimeEntry
import com.toggl.timer.domain.effects.startTimeEntryEffect
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.effects.continueTimeEntryEffect
import com.toggl.timer.domain.effects.editTimeEntryEffect
import com.toggl.timer.domain.states.TimeEntriesLogState
import com.toggl.timer.domain.states.TimerState
import java.util.*

internal val globalTimerReducer = Reducer<TimerState, TimerAction, IDataSource> { state, action, dataSource ->

    when(action) {
        TimeEntriesLogAction.StartTimeEntryButtonTapped -> {
            val description = state.value.editedDescription
            state.value = state.value.copy(editedDescription = "")
            startTimeEntryEffect(description, dataSource)
        }
        TimeEntriesLogAction.StopTimeEntryButtonTapped -> {
            val runningTimeEntry = state.value.runningTimeEntryOrNull()
            if (runningTimeEntry != null) {
                val duration = Date().time - runningTimeEntry.startTime.time
                editTimeEntryEffect(runningTimeEntry.copy(duration = duration), dataSource)
            } else empty()
        }
        is TimeEntriesLogAction.ContinueButtonTapped -> {
            val runningTimeEntry = state.value.runningTimeEntryOrNull()
            state.value.timeEntries.firstOrNull { it.id == action.id }?.let { timeEntryToContinue ->
                return@Reducer continueTimeEntryEffect(
                    timeEntryToContinue.description,
                    runningTimeEntry?.copy(duration = Date().time - runningTimeEntry.startTime.time),
                    dataSource
                )
            }
            empty()
        }
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
        is TimeEntriesLogAction.TimeEntryDescriptionChanged -> {
            state.value = state.value.copy(editedDescription = action.description)
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
