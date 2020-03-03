package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.repository.Repository
import com.toggl.timer.domain.actions.StartTimeEntryAction
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.effects.startTimeEntryEffect
import com.toggl.timer.domain.effects.stopTimeEntryEffect
import com.toggl.timer.domain.states.TimerState
import com.toggl.timer.domain.states.editedDescription
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

typealias TimerReducer = Reducer<TimerState, TimerAction, Repository>

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
internal fun createTimerModuleReducer() = TimerReducer { state, action, repository ->

    when(action) {
        is TimerAction.TimeEntryUpdated -> {
            val newTimeEntries = state.value.timeEntries.map {
                if (it.id != action.id) it else action.timeEntry
            }
            state.value = state.value.copy(timeEntries = newTimeEntries)
            noEffect()
        }
        is TimerAction.TimeEntryStarted -> {
            val newEntries =
                if (action.stoppedTimeEntry == null) state.value.timeEntries
                else state.value.timeEntries.map { if (it.id != action.stoppedTimeEntry.id) it else action.stoppedTimeEntry }

            state.value = state.value.copy(timeEntries = newEntries + action.startedTimeEntry)
            noEffect()
        }
        is TimerAction.TimeEntriesLog -> when(val logAction = action.timeEntriesLogAction) {
            is TimeEntriesLogAction.ContinueButtonTapped ->
                state.value.timeEntries.firstOrNull { it.id == logAction.id }?.run {
                    startTimeEntryEffect(description, repository)
                } ?: noEffect()
        }
        is TimerAction.StartTimeEntry -> when(action.startTimeEntryAction) {
            is StartTimeEntryAction.TimeEntryDescriptionChanged -> noEffect()
            StartTimeEntryAction.StopTimeEntryButtonTapped -> stopTimeEntryEffect(repository)
            StartTimeEntryAction.StartTimeEntryButtonTapped -> {
                val description = state.value.editedDescription
                state.value = state.value.copy(localState = state.value.localState.copy(editedDescription = ""))
                startTimeEntryEffect(description, repository)
            }
        }
    }
}