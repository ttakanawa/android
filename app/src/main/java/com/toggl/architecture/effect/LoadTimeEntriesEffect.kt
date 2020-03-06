package com.toggl.architecture.effect

import com.toggl.architecture.AppAction
import com.toggl.architecture.core.Effect
import com.toggl.repository.timeentry.TimeEntryRepository
import kotlinx.coroutines.flow.flow

fun loadTimeEntriesEffect(repository: TimeEntryRepository): Effect<AppAction> = flow {
    emit(AppAction.EntitiesLoaded(repository.loadTimeEntries()))
}