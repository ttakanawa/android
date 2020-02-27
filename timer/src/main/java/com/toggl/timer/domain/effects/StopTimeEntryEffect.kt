package com.toggl.timer.domain.effects

import com.toggl.architecture.core.Effect
import com.toggl.repository.Repository
import com.toggl.timer.domain.actions.TimerAction
import kotlinx.coroutines.flow.flow

fun stopTimeEntryEffect(repository: Repository): Effect<TimerAction> = flow {
    val stoppedTimeEntry = repository.stopRunningTimeEntry() ?: return@flow
    val action = TimerAction.TimeEntryUpdated(stoppedTimeEntry.id, stoppedTimeEntry)
    emit(action)
}