package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.repository.Repository
import com.toggl.timer.domain.actions.StartTimeEntryAction
import com.toggl.timer.domain.states.StartTimeEntryState

typealias StartTimeEntryReducer = Reducer<StartTimeEntryState, StartTimeEntryAction, Repository>

internal fun createStartTimeEntryReducer() = StartTimeEntryReducer { state, action, _ ->
    when(action) {
        StartTimeEntryAction.StartTimeEntryButtonTapped,
        StartTimeEntryAction.StopTimeEntryButtonTapped -> noEffect()
        is StartTimeEntryAction.TimeEntryDescriptionChanged -> {
            state.value = state.value.copy(editedDescription =  action.description)
            noEffect()
        }
    }
}

