package com.toggl.timer.start.domain

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.timer.common.domain.handleTimeEntryCreationStateChanges
import com.toggl.timer.common.domain.startTimeEntryEffect
import com.toggl.timer.extensions.replaceTimeEntryWithId

typealias StartTimeEntryReducer = Reducer<StartTimeEntryState, StartTimeEntryAction>

internal fun createStartTimeEntryReducer(repository: TimeEntryRepository) = StartTimeEntryReducer { state, action ->
        when (action) {
            StartTimeEntryAction.StopTimeEntryButtonTapped -> stopTimeEntryEffect(
                repository
            )
            StartTimeEntryAction.StartTimeEntryButtonTapped -> {
                val description = state.value.editedDescription
                state.value = state.value.copy(editedDescription = "")
                startTimeEntry(description, repository)
            }
            is StartTimeEntryAction.TimeEntryDescriptionChanged -> {
                state.value = state.value.copy(editedDescription = action.description)
                noEffect()
            }
            is StartTimeEntryAction.TimeEntryUpdated -> {
                val newTimeEntries = state.value.timeEntries
                    .replaceTimeEntryWithId(action.id, action.timeEntry)
                state.value = state.value.copy(timeEntries = newTimeEntries)
                noEffect()
            }
            is StartTimeEntryAction.TimeEntryStarted -> {
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

private fun startTimeEntry(description: String, repository: TimeEntryRepository) =
    startTimeEntryEffect(description, repository) {
        StartTimeEntryAction.TimeEntryStarted(it.startedTimeEntry, it.stoppedTimeEntry)
    }