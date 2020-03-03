package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.timer.domain.actions.StartTimeEntryAction
import com.toggl.timer.domain.states.StartTimeEntryState

internal val startTimeEntryReducer = Reducer<StartTimeEntryState, StartTimeEntryAction, Any> { state, action, _ ->
    when(action) {
        StartTimeEntryAction.StartTimeEntryButtonTapped -> noEffect()
        StartTimeEntryAction.StopTimeEntryButtonTapped -> noEffect()
        is StartTimeEntryAction.TimeEntryDescriptionChanged -> {
            state.value = state.value.copy(editedDescription =  action.description)
            noEffect()
        }
    }
}