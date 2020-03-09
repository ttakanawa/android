
package com.toggl.domain.effect

import com.toggl.architecture.core.Effect
import com.toggl.domain.AppAction
import com.toggl.repository.timeentry.TimeEntryRepository
import kotlinx.coroutines.flow.flow

fun loadTimeEntriesEffect(repository: TimeEntryRepository): Effect<AppAction> = flow {
    emit(AppAction.EntitiesLoaded(repository.loadTimeEntries()))
}
