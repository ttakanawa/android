package com.toggl.data

import com.toggl.models.domain.TimeEntry
import io.reactivex.rxjava3.core.Observable
import java.util.*

class MockDataSource : IDataSource {

    private var id: Long = 0

    override fun startTimeEntry(description: String): Observable<DatabaseOperation> {
        return Observable.fromIterable(mutableListOf<DatabaseOperation>(
            DatabaseOperation.Created(
                TimeEntry(
                    id = ++id,
                    description = description,
                    startTime = Date(),
                    duration = null
                )
            )
        ))
    }

    override fun editTimeEntry(timeEntry: TimeEntry): Observable<DatabaseOperation> {
        return Observable.fromIterable(mutableListOf<DatabaseOperation>(
            DatabaseOperation.Updated(
                timeEntry.id,
                TimeEntry(
                    id = timeEntry.id,
                    description = timeEntry.description,
                    startTime = timeEntry.startTime,
                    duration = timeEntry.duration
                )
            )
        ))
    }
}