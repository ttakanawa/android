package com.toggl.timer.log.domain

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.models.common.SwipeDirection
import com.toggl.models.domain.TimeEntry
import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.timer.common.domain.deleteTimeEntryEffect
import com.toggl.timer.common.domain.handleTimeEntryCreationStateChanges
import com.toggl.timer.common.domain.handleTimeEntryDeletionStateChanges
import com.toggl.timer.common.domain.startTimeEntryEffect
import com.toggl.timer.extensions.findEntryWithId

typealias TimeEntriesLogReducer = Reducer<TimeEntriesLogState, TimeEntriesLogAction>

internal fun createTimeEntriesLogReducer(repository: TimeEntryRepository) =
    TimeEntriesLogReducer { state, action ->
        when (action) {
            is TimeEntriesLogAction.ContinueButtonTapped -> {
                val timeEntryToContinue = state.value.timeEntries
                    .findEntryWithId(action.id) ?: return@TimeEntriesLogReducer noEffect()

                startTimeEntry(timeEntryToContinue, repository)
            }
            is TimeEntriesLogAction.TimeEntrySwiped -> {
                val swipedEntry = state.value.timeEntries
                    .findEntryWithId(action.id) ?: return@TimeEntriesLogReducer noEffect()

                when (action.direction) {
                    SwipeDirection.Left -> deleteTimeEntry(swipedEntry, repository)
                    SwipeDirection.Right -> startTimeEntry(swipedEntry, repository)
                }
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
            is TimeEntriesLogAction.TimeEntryDeleted -> {
                    state.value = state.value.copy(
                        timeEntries = handleTimeEntryDeletionStateChanges(
                            state.value.timeEntries,
                            action.deletedTimeEntry
                        )
                    )
                noEffect()
            }
        }
    }

private fun startTimeEntry(timeEntry: TimeEntry, repository: TimeEntryRepository) =
    startTimeEntryEffect(timeEntry.description, repository) {
        TimeEntriesLogAction.TimeEntryStarted(it.startedTimeEntry, it.stoppedTimeEntry)
    }

private fun deleteTimeEntry(timeEntry: TimeEntry, repository: TimeEntryRepository) =
    deleteTimeEntryEffect(timeEntry, repository) {
        TimeEntriesLogAction.TimeEntryDeleted(it)
    }
