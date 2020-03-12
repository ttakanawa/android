package com.toggl.timer.log.domain

import com.toggl.architecture.core.SettableValue
import com.toggl.models.domain.TimeEntry

fun createEmptyState(timeEntries: List<TimeEntry> = listOf()) =
    TimeEntriesLogState(
        timeEntries = timeEntries.associateBy { it.id },
        projects = mapOf(),
        editedTimeEntry = null
    )

fun TimeEntriesLogState.toSettableValue(setFunction: (TimeEntriesLogState) -> Unit) =
    SettableValue({ this }, setFunction)