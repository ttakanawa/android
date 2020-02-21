package com.toggl.data

import com.toggl.models.domain.TimeEntry
import io.reactivex.rxjava3.core.Observable
import java.util.*

class MockDataSource : IDataSource {

    private var id: Long = -1
    private var lastTime: Date = Date()

    override fun startTimeEntry(): Observable<DatabaseOperation> {

        val ops = mutableListOf<DatabaseOperation>()

        val now = Date()

        if (id != -1L) {
            ops.add(
                DatabaseOperation.Updated(
                    id,
                    TimeEntry(
                        id,
                        "Time entry number $id",
                        lastTime,
                        now.time - lastTime.time

                    )
                )
            )
        }

        ops.add(
            DatabaseOperation.Created(
                TimeEntry(
                    id = ++id,
                    description = "Time entry number $id",
                    startTime = Date(),
                    duration = null
                )
            )
        )

        return Observable.fromIterable(ops)
    }
}