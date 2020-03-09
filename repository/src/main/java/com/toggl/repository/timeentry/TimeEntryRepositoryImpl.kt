/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2020, Toggl
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * © 2020 GitHub, Inc.
 */
package com.toggl.repository.timeentry

import com.toggl.database.dao.TimeEntryDao
import com.toggl.models.domain.TimeEntry
import java.util.Date
import javax.inject.Inject

class TimeEntryRepositoryImpl @Inject constructor(private val timeEntryDao: TimeEntryDao) : TimeEntryRepository {

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
