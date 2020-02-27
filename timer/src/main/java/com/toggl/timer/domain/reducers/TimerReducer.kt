package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.combine
import com.toggl.architecture.core.pullback
import com.toggl.common.identity
import com.toggl.repository.Repository
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.effects.startTimeEntryEffect
import com.toggl.timer.domain.effects.stopTimeEntryEffect
import com.toggl.timer.domain.states.TimeEntriesLogState
import com.toggl.timer.domain.states.TimerState
import com.toggl.timer.domain.states.editedDescription
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
internal val globalTimerReducer = Reducer<TimerState, TimerAction, Repository> { state, action, repository ->

    when(action) {
        TimeEntriesLogAction.StartTimeEntryButtonTapped -> {
            val description = state.value.editedDescription
            state.value = state.value.copy(localState = state.value.localState.copy(editedDescription = ""))
            startTimeEntryEffect(description, repository)
        }
        TimeEntriesLogAction.StopTimeEntryButtonTapped -> {
            stopTimeEntryEffect(repository)
        }
        is TimeEntriesLogAction.ContinueButtonTapped -> {
            state.value.timeEntries.firstOrNull { it.id == action.id }?.run {
                startTimeEntryEffect(description,  repository)
            } ?: emptyFlow()
        }
        is TimerAction.TimeEntryUpdated -> {
            val newTimeEntries = state.value.timeEntries.map {
                if (it.id != action.id) it else action.timeEntry
            }
            state.value = state.value.copy(timeEntries = newTimeEntries)
            emptyFlow()
        }
        is TimeEntriesLogAction.TimeEntryDescriptionChanged -> {
            state.value = state.value.copy(localState =  state.value.localState.copy(editedDescription = action.description))
            emptyFlow()
        }
        is TimerAction.TimeEntryStarted -> {
            val newEntries =
                if (action.stoppedTimeEntry == null) state.value.timeEntries
                else state.value.timeEntries.map { if (it.id != action.stoppedTimeEntry.id) it else action.stoppedTimeEntry }

            state.value = state.value.copy(timeEntries = newEntries + action.startedTimeEntry)

            emptyFlow()
        }
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val timerReducer =
    combine (
        globalTimerReducer,
        pullback<TimeEntriesLogState, TimerState, TimeEntriesLogAction, TimerAction, Any, Repository>(
            reducer = timeEntriesLogReducer,
            mapToLocalState = TimeEntriesLogState.Companion::fromTimerState,
            mapToLocalAction = { it as? TimeEntriesLogAction },
            mapToLocalEnvironment = ::identity,
            mapToGlobalAction = ::identity,
            mapToGlobalState = { global: TimerState, local ->
                global.copy(
                    localState = global.localState.copy(
                        editedDescription = local.editedDescription
                    )
                )
            }
        )
    )
