package com.toggl.timer.start.domain

import com.toggl.architecture.core.Effect
import com.toggl.repository.Repository
import com.toggl.timer.common.domain.TimerAction
import com.toggl.timer.start.domain.StartTimeEntryAction
import kotlinx.coroutines.flow.flow

fun stopTimeEntryEffect(repository: Repository): Effect<StartTimeEntryAction> = flow {
    val stoppedTimeEntry = repository.stopRunningTimeEntry() ?: return@flow
    val action = StartTimeEntryAction.TimeEntryUpdated(stoppedTimeEntry.id, stoppedTimeEntry)
    emit(action)
}