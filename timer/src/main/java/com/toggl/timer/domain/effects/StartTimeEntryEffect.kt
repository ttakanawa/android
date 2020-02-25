package com.toggl.timer.domain.effects

import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.toEffect
import com.toggl.data.DatabaseOperation
import com.toggl.data.IDataSource
import com.toggl.models.domain.TimeEntry
import com.toggl.timer.domain.actions.TimerAction
import io.reactivex.rxjava3.core.Observable

fun startTimeEntryEffect(description: String, dataSource: IDataSource): Effect<TimerAction> =
    dataSource.startTimeEntry(description)
        .map(::toTimerAction)
        .toEffect()

fun editTimeEntryEffect(timerAction: TimeEntry, dataSource: IDataSource): Effect<TimerAction> =
    dataSource.editTimeEntry(timerAction)
        .map(::toTimerAction)
        .toEffect()

fun continueTimeEntryEffect(description: String, timerAction: TimeEntry?, dataSource: IDataSource): Effect<TimerAction> {
    return if (timerAction != null) {
        Observable.merge(dataSource.startTimeEntry(description), dataSource.editTimeEntry(timerAction))
            .map(::toTimerAction)
            .toEffect()
    } else {
        startTimeEntryEffect(description, dataSource)
    }
}


private fun toTimerAction(databaseOperation: DatabaseOperation): TimerAction =
    when(databaseOperation) {
        is DatabaseOperation.Created -> TimerAction.TimeEntryCreated(databaseOperation.timeEntry)
        is DatabaseOperation.Updated -> TimerAction.TimeEntryUpdated(databaseOperation.id, databaseOperation.timeEntry)
    }
