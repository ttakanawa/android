package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Effect.Companion.empty
import com.toggl.architecture.core.Reducer
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.states.TimeEntriesLogState

internal val timeEntriesLogReducer = Reducer<TimeEntriesLogState, TimeEntriesLogAction, Any> { state, action, _ ->
    when(action) {
        TimeEntriesLogAction.StartTimeEntryButtonTapped -> {}
        is TimeEntriesLogAction.ContinueButtonTapped -> TODO()
    }

    empty()
}