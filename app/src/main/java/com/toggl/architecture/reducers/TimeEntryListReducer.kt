package com.toggl.architecture.reducers

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.architecture.effect.loadTimeEntriesEffect
import com.toggl.environment.AppEnvironment

fun createTimeEntryListReducer() = Reducer <AppState, AppAction, AppEnvironment> { state, action, environment ->
    when (action) {
        AppAction.InitTimeEntries -> loadTimeEntriesEffect(environment.timeEntryRepository)
        is AppAction.TimeEntriesLoaded -> {
            state.value = state.value.copy(timeEntries = action.timeEntries)
            noEffect()
        }
        else -> noEffect()
    }
}