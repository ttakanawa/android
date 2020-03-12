package com.toggl.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.domain.effect.loadTimeEntriesEffect
import com.toggl.repository.timeentry.TimeEntryRepository

typealias TimeEntryListReducer = Reducer<AppState, AppAction>

fun createTimeEntryListReducer(repository: TimeEntryRepository) =
    Reducer<AppState, AppAction> { state, action ->
        when (action) {
            AppAction.Load -> loadTimeEntriesEffect(repository)
            is AppAction.EntitiesLoaded -> {
                state.value = state.value.copy(
                    timeEntries = action.timeEntries.associateBy { it.id }
                )
                noEffect()
            }
            is AppAction.Onboarding,
            is AppAction.Timer -> noEffect()
        }
    }
