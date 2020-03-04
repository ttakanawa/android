package com.toggl.timer.common.domain

import com.toggl.architecture.core.Effect
import com.toggl.repository.Repository
import com.toggl.timer.common.domain.TimerAction
import kotlinx.coroutines.flow.flow

fun startTimeEntryEffect(description: String, repository: Repository): Effect<TimerAction> = flow {
    val startTimeEntryResult = repository.startTimeEntry(description)
    val action = TimerAction.TimeEntryStarted(startTimeEntryResult.startedTimeEntry, startTimeEntryResult.stoppedTimeEntry)
    emit(action)
}

