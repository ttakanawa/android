package com.toggl.data

import com.toggl.models.domain.TimeEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class MockDataSource : IDataSource {

    private var id: Long = 0

    override fun startTimeEntry(description: String): Flow<DatabaseOperation> = flow {
        emit(
            DatabaseOperation.Created(
                TimeEntry(
                    id = ++id,
                    description = description,
                    startTime = Date(),
                    duration = null
                )
            )
        )
    }

    override fun editTimeEntry(timeEntry: TimeEntry): Flow<DatabaseOperation> = flow {
        emit(
            DatabaseOperation.Updated(
                timeEntry.id,
                TimeEntry(
                    id = timeEntry.id,
                    description = timeEntry.description,
                    startTime = timeEntry.startTime,
                    duration = timeEntry.duration
                )
            )
        )
    }
}