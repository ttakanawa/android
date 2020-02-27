package com.toggl.timer.domain.effects

import com.toggl.architecture.core.Effect
import com.toggl.repository.Repository
import com.toggl.timer.domain.actions.TimerAction
import kotlinx.coroutines.flow.flow

fun startTimeEntryEffect(description: String, repository: Repository): Effect<TimerAction> = flow {
    val startTimeEntryResult = repository.startTimeEntry(description)
    val action = TimerAction.TimeEntryStarted(startTimeEntryResult.startedTimeEntry, startTimeEntryResult.stoppedTimeEntry)
    emit(action)
}

