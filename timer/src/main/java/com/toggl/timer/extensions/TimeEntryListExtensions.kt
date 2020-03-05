package com.toggl.timer.extensions

import com.toggl.models.domain.TimeEntry
import com.toggl.timer.log.domain.FlatTimeEntryItem

fun List<TimeEntry>.replaceTimeEntryWithId(id: Long, timeEntryToReplace: TimeEntry) =
    map { if (it.id == id) timeEntryToReplace else it }

fun List<TimeEntry>.runningTimeEntryOrNull() =
    firstOrNull { it.duration == null }

fun List<TimeEntry>.toTimeEntryViewModelList() =
    filter { it.duration != null }
    .map { timeEntry ->
        FlatTimeEntryItem(
            id = timeEntry.id,
            description = timeEntry.description,
            startTime = timeEntry.startTime,
            duration = timeEntry.duration
        )
    }