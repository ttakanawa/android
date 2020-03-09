
package com.toggl.timer.start.domain

import com.toggl.architecture.core.Effect
import com.toggl.repository.timeentry.TimeEntryRepository
import kotlinx.coroutines.flow.flow

fun stopTimeEntryEffect(repository: TimeEntryRepository): Effect<StartTimeEntryAction> = flow {
    val stoppedTimeEntry = repository.stopRunningTimeEntry() ?: return@flow
    val action = StartTimeEntryAction.TimeEntryUpdated(stoppedTimeEntry.id, stoppedTimeEntry)
    emit(action)
}
