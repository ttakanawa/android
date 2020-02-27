package com.toggl.timer.domain

import com.toggl.data.db.TimeEntryDao
import com.toggl.models.domain.TimeEntry
import com.toggl.timer.domain.TimeEntryLogAction.TimeEntryLogUpdated
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import java.util.*

fun observeTimeEntries(dataSource: TimeEntryDao): Flow<TimeEntryLogAction> =
    dataSource.getAll().map { TimeEntryLogUpdated(it) }

fun startTimeEntry(description: String, dataSource: TimeEntryDao): Flow<TimeEntryLogAction> {
    dataSource.insertAll(TimeEntry(description = description, startTime = Date(), duration = null))
    return emptyFlow() // TODO return Success
}

fun stopTimeEntry(timeEntry: TimeEntry, dataSource: TimeEntryDao): Flow<TimeEntryLogAction> {
    dataSource.update(timeEntry.copy(duration = Date().time - timeEntry.startTime.time))
    return emptyFlow() // TODO return Success
}

fun stopAndContinueTimeEntry(timeEntryToStop: TimeEntry, descriptionToContinue: String, dataSource: TimeEntryDao): Flow<TimeEntryLogAction> {
    stopTimeEntry(timeEntryToStop, dataSource)
    return startTimeEntry(descriptionToContinue, dataSource)
}