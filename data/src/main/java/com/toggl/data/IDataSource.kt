package com.toggl.data

import com.toggl.models.domain.TimeEntry
import kotlinx.coroutines.flow.Flow

sealed class DatabaseOperation {
    class Created(val timeEntry: TimeEntry) : DatabaseOperation()
    class Updated(val id: Long, val timeEntry: TimeEntry) : DatabaseOperation()
}

interface IDataSource {
    fun startTimeEntry(description: String): Flow<DatabaseOperation>
    fun editTimeEntry(timeEntry: TimeEntry): Flow<DatabaseOperation>
}