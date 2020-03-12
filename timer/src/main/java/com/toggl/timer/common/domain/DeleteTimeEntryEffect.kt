package com.toggl.timer.common.domain

import com.toggl.architecture.core.Effect
import com.toggl.models.domain.TimeEntry
import com.toggl.repository.timeentry.TimeEntryRepository
import kotlinx.coroutines.flow.flow

fun <Action> deleteTimeEntriesEffect(
    timeEntriesToDelete: List<TimeEntry>,
    repository: TimeEntryRepository,
    mapFn: (HashSet<TimeEntry>) -> Action
): Effect<Action> = flow {
    val deletedTimeEntries = repository.deleteTimeEntries(timeEntriesToDelete)
    val action = mapFn(deletedTimeEntries)
    emit(action)
}
