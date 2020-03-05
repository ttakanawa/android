package com.toggl.timer.common.domain

import com.toggl.architecture.core.Reducer
import com.toggl.models.domain.TimeEntry
import com.toggl.repository.Repository

typealias TimerReducer = Reducer<TimerState, TimerAction, Repository>

fun handleTimeEntryCreationStateChanges(
    timeEntries: List<TimeEntry>,
    startedTimeEntry: TimeEntry,
    stoppedTimeEntry: TimeEntry?
) : List<TimeEntry> {

    val newEntries =
        if (stoppedTimeEntry == null) timeEntries
        else timeEntries.map { if (it.id != stoppedTimeEntry.id) it else stoppedTimeEntry }

    return newEntries + startedTimeEntry
}