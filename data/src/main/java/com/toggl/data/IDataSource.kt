package com.toggl.data

import com.toggl.models.domain.TimeEntry
import io.reactivex.rxjava3.core.Observable

sealed class DatabaseOperation {
    class Created(val timeEntry: TimeEntry) : DatabaseOperation()
    class Updated(val id: Long, val timeEntry: TimeEntry) : DatabaseOperation()
}

interface IDataSource {
    fun startTimeEntry(): Observable<DatabaseOperation>
}