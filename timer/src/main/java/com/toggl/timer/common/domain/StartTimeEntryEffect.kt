package com.toggl.timer.common.domain

import com.toggl.architecture.core.Effect
import com.toggl.repository.timeentry.StartTimeEntryResult
import com.toggl.repository.timeentry.TimeEntryRepository
import kotlinx.coroutines.flow.flow

fun <Action> startTimeEntryEffect(
    description: String,
    repository: TimeEntryRepository,
    mapFn: (StartTimeEntryResult) -> Action
): Effect<Action> = flow {
    val startTimeEntryResult = repository.startTimeEntry(description)
    val action = mapFn(startTimeEntryResult)
    emit(action)
}
