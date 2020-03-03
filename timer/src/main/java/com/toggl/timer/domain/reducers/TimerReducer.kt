package com.toggl.timer.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.combine
import com.toggl.architecture.core.noEffect
import com.toggl.architecture.core.pullback
import com.toggl.common.identity
import com.toggl.repository.Repository
import com.toggl.timer.domain.actions.StartTimeEntryAction
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.effects.startTimeEntryEffect
import com.toggl.timer.domain.effects.stopTimeEntryEffect
import com.toggl.timer.domain.states.StartTimeEntryState
import com.toggl.timer.domain.states.TimeEntriesLogState
import com.toggl.timer.domain.states.TimerState
import com.toggl.timer.domain.states.editedDescription
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
internal val timerModuleReducer = Reducer<TimerState, TimerAction, Repository> { state, action, repository ->

    when(action) {
        is StartTimeEntryAction.TimeEntryDescriptionChanged -> noEffect()
        is TimeEntriesLogAction.ContinueButtonTapped ->  noEffect()
        StartTimeEntryAction.StopTimeEntryButtonTapped -> stopTimeEntryEffect(repository)
        StartTimeEntryAction.StartTimeEntryButtonTapped -> {
            val description = state.value.editedDescription
            state.value = state.value.copy(localState = state.value.localState.copy(editedDescription = ""))
            startTimeEntryEffect(description, repository)
        }
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
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val timerReducer =
    combine (
        timerModuleReducer,
        pullback<TimeEntriesLogState, TimerState, TimeEntriesLogAction, TimerAction, Repository, Repository>(
            reducer = timeEntriesLogReducer,
            mapToLocalState = TimeEntriesLogState.Companion::fromTimerState,
            mapToLocalAction = TimeEntriesLogAction.Companion::fromTimerAction,
            mapToLocalEnvironment = ::identity,
            mapToGlobalAction = ::identity,
            mapToGlobalState = { global: TimerState, _ -> global }
        ),
        pullback<StartTimeEntryState, TimerState, StartTimeEntryAction, TimerAction, Any, Repository>(
            reducer = startTimeEntryReducer,
            mapToLocalState = StartTimeEntryState.Companion::fromTimerState,
            mapToLocalAction = StartTimeEntryAction.Companion::fromTimerAction,
            mapToLocalEnvironment = ::identity,
            mapToGlobalAction = ::identity,
            mapToGlobalState = StartTimeEntryState.Companion::toTimerState
        )
    )
