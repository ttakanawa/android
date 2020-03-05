package com.toggl.timer.common.domain

import com.toggl.architecture.core.Effect
import com.toggl.repository.Repository
import com.toggl.repository.StartTimeEntryResult
import com.toggl.timer.common.domain.TimerAction
import kotlinx.coroutines.flow.flow

fun <Action> startTimeEntryEffect(description: String, repository: Repository, mapFn: (StartTimeEntryResult) -> Action): Effect<Action> = flow<Action> {
    val startTimeEntryResult = repository.startTimeEntry(description)
    val action = mapFn(startTimeEntryResult)
    emit(action)
}
