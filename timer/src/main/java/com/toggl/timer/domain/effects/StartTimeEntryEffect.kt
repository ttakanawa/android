package com.toggl.timer.domain.effects

import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.toEffect
import com.toggl.data.DatabaseOperation
import com.toggl.data.IDataSource
import com.toggl.timer.domain.actions.TimerAction

fun startTimeEntryEffect(dataSource: IDataSource): Effect<TimerAction> =
    dataSource.startTimeEntry()
        .map(::toTimerAction)
        .toEffect()


private fun toTimerAction(databaseOperation: DatabaseOperation): TimerAction =
    when(databaseOperation) {
        is DatabaseOperation.Created -> TimerAction.TimeEntryCreated(databaseOperation.timeEntry)
        is DatabaseOperation.Updated -> TimerAction.TimeEntryUpdated(databaseOperation.id, databaseOperation.timeEntry)
    }
