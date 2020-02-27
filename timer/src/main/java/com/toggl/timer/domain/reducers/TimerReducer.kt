package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.combine
import com.toggl.architecture.core.pullback
import com.toggl.common.identity
import com.toggl.data.IDataSource
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.effects.continueTimeEntryEffect
import com.toggl.timer.domain.effects.editTimeEntryEffect
import com.toggl.timer.domain.effects.startTimeEntryEffect
import com.toggl.timer.domain.states.TimeEntriesLogState
import com.toggl.timer.domain.states.TimerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import java.util.*

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
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
            } else emptyFlow()
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
            emptyFlow()
        }
        is TimerAction.TimeEntryUpdated -> {
            val newTimeEntries = state.value.timeEntries.map {
                if (it.id != action.id) it else action.timeEntry
            }
            state.value = state.value.copy(timeEntries = newTimeEntries)
            emptyFlow()
        }
        is TimerAction.TimeEntryCreated -> {
            state.value = state.value.copy(timeEntries = state.value.timeEntries + action.timeEntry)
            emptyFlow()
        }
        is TimeEntriesLogAction.TimeEntryDescriptionChanged -> {
            state.value = state.value.copy(editedDescription = action.description)
            emptyFlow()
        }
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
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
