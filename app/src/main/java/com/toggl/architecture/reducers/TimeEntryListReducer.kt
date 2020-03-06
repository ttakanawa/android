package com.toggl.architecture.reducers

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.architecture.effect.loadTimeEntriesEffect

import com.toggl.repository.timeentry.TimeEntryRepository

typealias TimeEntryListReducer = Reducer<AppState, AppAction>

fun createTimeEntryListReducer(repository: TimeEntryRepository) = Reducer <AppState, AppAction> { state, action ->
    when (action) {
        AppAction.Load -> loadTimeEntriesEffect(repository)
        is AppAction.EntitiesLoaded -> {
            state.value = state.value.copy(timeEntries = action.timeEntries)
            noEffect()
        }
        else -> noEffect()
    }
}