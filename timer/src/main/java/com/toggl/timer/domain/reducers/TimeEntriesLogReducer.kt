package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.states.TimeEntriesLogState
import kotlinx.coroutines.flow.emptyFlow

internal val timeEntriesLogReducer = Reducer<TimeEntriesLogState, TimeEntriesLogAction, Any> { _, action, _ ->
    when(action) {
        TimeEntriesLogAction.StartTimeEntryButtonTapped -> {}
        is TimeEntriesLogAction.ContinueButtonTapped -> {}
    }
    emptyFlow()
}