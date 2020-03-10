package com.toggl.timer.common.domain

import com.toggl.architecture.core.Effect
import com.toggl.models.domain.TimeEntry
import com.toggl.repository.timeentry.TimeEntryRepository
import kotlinx.coroutines.flow.flow

fun <Action> deleteTimeEntryEffect(
    timeEntryToDelete: TimeEntry,
    repository: TimeEntryRepository,
    mapFn: (TimeEntry) -> Action
): Effect<Action> = flow {
    val deletedTimeEntry = repository.deleteTimeEntry(timeEntryToDelete)
    val action = mapFn(deletedTimeEntry)
    emit(action)
}
