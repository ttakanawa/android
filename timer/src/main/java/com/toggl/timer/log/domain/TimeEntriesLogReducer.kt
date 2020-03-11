package com.toggl.timer.log.domain

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.timer.common.domain.handleTimeEntryCreationStateChanges
import com.toggl.timer.common.domain.startTimeEntryEffect
import java.lang.IllegalStateException

typealias TimeEntriesLogReducer = Reducer<TimeEntriesLogState, TimeEntriesLogAction>

internal fun createTimeEntriesLogReducer(repository: TimeEntryRepository) =
    TimeEntriesLogReducer { state, action ->
        when (action) {
            is TimeEntriesLogAction.TimeEntryTapped -> {
                val tappedTimeEntry = state.value.timeEntries
                    .firstOrNull { it.id == action.id }
                    ?: throw IllegalStateException()

                state.value = state.value.copy(editedTimeEntry = tappedTimeEntry)
                noEffect()
            }
            is TimeEntriesLogAction.ContinueButtonTapped -> {
                val timeEntryToContinue = state.value.timeEntries
                    .firstOrNull { it.id == action.id }
                ?: throw IllegalStateException()

                startTimeEntry(timeEntryToContinue.description, repository)
            }
            is TimeEntriesLogAction.TimeEntryStarted -> {
                state.value = state.value.copy(
                    timeEntries = handleTimeEntryCreationStateChanges(
                        state.value.timeEntries,
                        action.startedTimeEntry,
                        action.stoppedTimeEntry
                    )
                )
                noEffect()
            }
        }
    }

internal fun startTimeEntry(description: String, repository: TimeEntryRepository) =
    startTimeEntryEffect(description, repository) {
        TimeEntriesLogAction.TimeEntryStarted(it.startedTimeEntry, it.stoppedTimeEntry)
    }
