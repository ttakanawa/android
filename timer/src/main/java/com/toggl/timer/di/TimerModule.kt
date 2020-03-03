package com.toggl.timer.di

import com.toggl.architecture.core.Store
import com.toggl.architecture.core.combine
import com.toggl.architecture.core.pullback
import com.toggl.common.identity
import com.toggl.repository.Repository
import com.toggl.timer.domain.actions.StartTimeEntryAction
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.reducers.TimerReducer
import com.toggl.timer.domain.reducers.createStartTimeEntryReducer
import com.toggl.timer.domain.reducers.createTimeEntriesLogReducer
import com.toggl.timer.domain.reducers.createTimerModuleReducer
import com.toggl.timer.domain.states.StartTimeEntryState
import com.toggl.timer.domain.states.TimeEntriesLogState
import com.toggl.timer.domain.states.TimerState
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton
@Module(subcomponents = [TimerComponent::class])
class TimerModule {

    @ExperimentalCoroutinesApi
    @Provides
    internal fun timeEntriesLogStore(store: Store<TimerState, TimerAction>): Store<TimeEntriesLogState, TimeEntriesLogAction> =
        store.view(
            mapToLocalState = TimeEntriesLogState.Companion::fromTimerState,
            mapToGlobalAction = TimeEntriesLogAction.Companion::toTimerAction
        )

    @ExperimentalCoroutinesApi
    @Provides
    internal fun startTimeEntryStore(store: Store<TimerState, TimerAction>): Store<StartTimeEntryState, StartTimeEntryAction> =
        store.view(
            mapToLocalState = StartTimeEntryState.Companion::fromTimerState,
            mapToGlobalAction = StartTimeEntryAction.Companion::toTimerAction
        )

    @Provides
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Singleton
    internal fun timerReducer() : TimerReducer {

        return combine (
            createTimerModuleReducer(),
            createTimeEntriesLogReducer().pullback(
                mapToLocalState = TimeEntriesLogState.Companion::fromTimerState,
                mapToLocalAction = TimeEntriesLogAction.Companion::fromTimerAction,
                mapToGlobalAction = TimeEntriesLogAction.Companion::toTimerAction,
                mapToGlobalState = { global: TimerState, _ -> global }
            ),
            createStartTimeEntryReducer().pullback(
                mapToLocalState = StartTimeEntryState.Companion::fromTimerState,
                mapToLocalAction = StartTimeEntryAction.Companion::fromTimerAction,
                mapToGlobalAction = StartTimeEntryAction.Companion::toTimerAction,
                mapToGlobalState = StartTimeEntryState.Companion::toTimerState
            )
        )
    }
}

