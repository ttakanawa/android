package com.toggl.timer.common.domain

import com.toggl.architecture.core.Reducer
import com.toggl.models.domain.TimeEntry

typealias TimerReducer = Reducer<TimerState, TimerAction>

fun handleTimeEntryCreationStateChanges(
    timeEntries: List<TimeEntry>,
    startedTimeEntry: TimeEntry,
    stoppedTimeEntry: TimeEntry?
): List<TimeEntry> {

    val newEntries =
        if (stoppedTimeEntry == null) timeEntries
        else timeEntries.map { if (it.id != stoppedTimeEntry.id) it else stoppedTimeEntry }

    return newEntries + startedTimeEntry
}

fun handleTimeEntryDeletionStateChanges(
    timeEntries: List<TimeEntry>,
    deletedTimeEntry: TimeEntry
): List<TimeEntry> =
    timeEntries.filter { it.id != deletedTimeEntry.id }