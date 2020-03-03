package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.repository.Repository
import com.toggl.timer.domain.actions.StartTimeEntryAction
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.effects.startTimeEntryEffect
import com.toggl.timer.domain.states.TimeEntriesLogState
import kotlinx.coroutines.flow.emptyFlow

typealias TimeEntriesLogReducer = Reducer<TimeEntriesLogState, TimeEntriesLogAction, Repository>

internal fun createTimeEntriesLogReducer() = TimeEntriesLogReducer { _, action, _ ->
    when(action) {
        is TimeEntriesLogAction.ContinueButtonTapped -> noEffect()
    }
}