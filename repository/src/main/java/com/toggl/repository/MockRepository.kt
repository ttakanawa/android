package com.toggl.repository

import com.toggl.models.domain.TimeEntry
import java.util.*

class MockRepository : Repository {

    private var timeEntries = mutableListOf<TimeEntry>()

    override suspend fun startTimeEntry(description: String): StartTimeEntryResult {

        val now = Date()
        val oldEntry = timeEntries.firstOrNull { it.duration == null }?.run {
            val newEntry = copy(duration = now.time - startTime.time)
            timeEntries[id.toInt()] = newEntry
            newEntry
        }

        val currentId = timeEntries.size;
        timeEntries.add(TimeEntry(
            id = currentId.toLong(),
            description = description,
            startTime = now,
            duration = null
        ))

        return StartTimeEntryResult(timeEntries[currentId], oldEntry)
    }

    override suspend fun stopRunningTimeEntry(): TimeEntry? =
        timeEntries.firstOrNull{ it.duration == null }?.run {
            val newEntry = copy(duration = Date().time - startTime.time)
            timeEntries[id.toInt()] = newEntry
            newEntry
        }

    override suspend fun editTimeEntry(timeEntry: TimeEntry): TimeEntry {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}