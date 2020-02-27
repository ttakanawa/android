package com.toggl.repository

import com.toggl.models.domain.TimeEntry

data class StartTimeEntryResult(
    val startedTimeEntry: TimeEntry,
    val stoppedTimeEntry : TimeEntry?
)

interface Repository {
    suspend fun startTimeEntry(description: String): StartTimeEntryResult
    suspend fun stopRunningTimeEntry() : TimeEntry?
    suspend fun editTimeEntry(timeEntry: TimeEntry): TimeEntry
}