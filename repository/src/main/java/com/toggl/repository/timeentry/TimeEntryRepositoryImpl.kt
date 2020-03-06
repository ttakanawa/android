package com.toggl.repository.timeentry

import com.toggl.database.dao.TimeEntryDao
import com.toggl.models.domain.TimeEntry
import java.util.*
import javax.inject.Inject

class TimeEntryRepositoryImpl @Inject constructor(private val timeEntryDao: TimeEntryDao): TimeEntryRepository {

    override suspend fun loadTimeEntries() = timeEntryDao.getAll()

    override suspend fun startTimeEntry(description: String): StartTimeEntryResult {
        val oldStoppedTimeEntries = stopAllRunningTimeEntries()
        val id = timeEntryDao.insert(
            TimeEntry(
                description = description,
                startTime = Date(),
                duration = null,
                billable = false,
                projectId = null,
                taskId = null
            )
        )
        return StartTimeEntryResult(
            timeEntryDao.getOne(id),
            oldStoppedTimeEntries.firstOrNull()
        )
    }

    override suspend fun stopRunningTimeEntry(): TimeEntry? {
        val oldStoppedTimeEntries = stopAllRunningTimeEntries()
        timeEntryDao.updateAll(oldStoppedTimeEntries)
        return oldStoppedTimeEntries.firstOrNull()
    }

    override suspend fun editTimeEntry(timeEntry: TimeEntry): TimeEntry {
        TODO("not implemented")
    }

    private suspend fun stopAllRunningTimeEntries(): List<TimeEntry> {
        val now = Date()
        val oldRunningTimeEntries = timeEntryDao.getAllRunning()
        return oldRunningTimeEntries.map { it.copy(duration = now.time - it.startTime.time) }
    }
}