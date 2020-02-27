package com.toggl.timer.domain.effects

import com.toggl.architecture.core.Effect
import com.toggl.architecture.extensions.merge
import com.toggl.data.DatabaseOperation
import com.toggl.data.IDataSource
import com.toggl.models.domain.TimeEntry
import com.toggl.timer.domain.actions.TimerAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.map

fun startTimeEntryEffect(description: String, dataSource: IDataSource): Effect<TimerAction> =
    dataSource.startTimeEntry(description)
        .map { toTimerAction(it) }


fun editTimeEntryEffect(timerAction: TimeEntry, dataSource: IDataSource): Effect<TimerAction> =
    dataSource.editTimeEntry(timerAction)
        .map { toTimerAction(it) }

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun continueTimeEntryEffect(description: String, timerAction: TimeEntry?, dataSource: IDataSource): Effect<TimerAction> {
    return if (timerAction != null) {
        dataSource.startTimeEntry(description)
            .merge(dataSource.editTimeEntry(timerAction))
            .map { toTimerAction(it) }
    } else {
        startTimeEntryEffect(description, dataSource)
    }
}


private fun toTimerAction(databaseOperation: DatabaseOperation): TimerAction =
    when(databaseOperation) {
        is DatabaseOperation.Created -> TimerAction.TimeEntryCreated(databaseOperation.timeEntry)
        is DatabaseOperation.Updated -> TimerAction.TimeEntryUpdated(databaseOperation.id, databaseOperation.timeEntry)
    }
