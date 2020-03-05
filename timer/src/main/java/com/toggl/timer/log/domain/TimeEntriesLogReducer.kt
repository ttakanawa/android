package com.toggl.timer.log.domain

import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.repository.Repository
import com.toggl.timer.common.domain.TimerAction
import com.toggl.timer.common.domain.handleTimeEntryCreationStateChanges
import com.toggl.timer.common.domain.startTimeEntryEffect

typealias TimeEntriesLogReducer = Reducer<TimeEntriesLogState, TimeEntriesLogAction, Repository>

internal fun createTimeEntriesLogReducer() = TimeEntriesLogReducer { state, action, repository ->
    when (action) {
        is TimeEntriesLogAction.ContinueButtonTapped ->
            state.value.timeEntries.firstOrNull { it.id == action.id }?.run {
                startTimeEntry(description, repository)
            } ?: noEffect()
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

private fun startTimeEntry(description: String, repository: Repository) =
    startTimeEntryEffect(description, repository) {
        TimeEntriesLogAction.TimeEntryStarted(it.startedTimeEntry, it.stoppedTimeEntry)
    }