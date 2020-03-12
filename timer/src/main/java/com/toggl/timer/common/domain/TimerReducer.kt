package com.toggl.timer.common.domain

import com.toggl.architecture.core.Reducer
import com.toggl.models.domain.TimeEntry
import com.toggl.timer.extensions.replaceTimeEntryWithId

typealias TimerReducer = Reducer<TimerState, TimerAction>

fun handleTimeEntryCreationStateChanges(
    timeEntries: Map<Long, TimeEntry>,
    startedTimeEntry: TimeEntry,
    stoppedTimeEntry: TimeEntry?
): Map<Long,TimeEntry> {

    val newEntries = timeEntries.toMutableMap()
    newEntries[startedTimeEntry.id] = startedTimeEntry
    if (stoppedTimeEntry != null) {
        newEntries[stoppedTimeEntry.id] = stoppedTimeEntry
    }

    return newEntries.toMap()
}

fun handleTimeEntryDeletionStateChanges(
    timeEntries: List<TimeEntry>,
    deletedTimeEntry: TimeEntry
): List<TimeEntry> =
    timeEntries.filter { it.id != deletedTimeEntry.id }