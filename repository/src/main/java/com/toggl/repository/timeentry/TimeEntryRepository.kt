package com.toggl.repository.timeentry

import com.toggl.models.domain.TimeEntry

data class StartTimeEntryResult(
    val startedTimeEntry: TimeEntry,
    val stoppedTimeEntry: TimeEntry?
)

interface TimeEntryRepository {
    suspend fun loadTimeEntries(): List<TimeEntry>
    suspend fun startTimeEntry(description: String): StartTimeEntryResult
    suspend fun stopRunningTimeEntry(): TimeEntry?
    suspend fun editTimeEntry(timeEntry: TimeEntry): TimeEntry
    suspend fun deleteTimeEntries(timeEntries: List<TimeEntry>): HashSet<TimeEntry>
}
