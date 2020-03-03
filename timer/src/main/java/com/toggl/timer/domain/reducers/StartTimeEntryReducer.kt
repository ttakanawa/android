package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.timer.domain.actions.StartTimeEntryAction
import com.toggl.timer.domain.states.StartTimeEntryState
import kotlinx.coroutines.flow.emptyFlow

internal val startTimeEntryReducer = Reducer<StartTimeEntryState, StartTimeEntryAction, Any> { state, action, _ ->
    when(action) {
        StartTimeEntryAction.StartTimeEntryButtonTapped -> emptyFlow()
        StartTimeEntryAction.StopTimeEntryButtonTapped -> emptyFlow()
        is StartTimeEntryAction.TimeEntryDescriptionChanged -> {
            state.value = state.value.copy(editedDescription =  action.description)
            emptyFlow()
        }
    }
}