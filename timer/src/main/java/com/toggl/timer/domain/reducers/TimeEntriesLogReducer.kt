package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.repository.Repository
import com.toggl.timer.domain.actions.StartTimeEntryAction
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.effects.startTimeEntryEffect
import com.toggl.timer.domain.states.TimeEntriesLogState
import kotlinx.coroutines.flow.emptyFlow

internal val timeEntriesLogReducer = Reducer<TimeEntriesLogState, TimeEntriesLogAction, Repository> { state, action, repository ->
    when(action) {
        is TimeEntriesLogAction.ContinueButtonTapped -> {
            state.value.items.firstOrNull { it.id == action.id }?.run {
                startTimeEntryEffect(description, repository)
            } ?: emptyFlow()
        }
    }
    emptyFlow()
}