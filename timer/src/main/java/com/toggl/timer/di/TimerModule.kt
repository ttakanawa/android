package com.toggl.timer.di

import com.toggl.architecture.core.Store
import com.toggl.common.identity
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.actions.TimerAction
import com.toggl.timer.domain.states.TimeEntriesLogState
import com.toggl.timer.domain.states.TimerState
import dagger.Module
import dagger.Provides


@Module(subcomponents = [TimerComponent::class])
class TimerModule {
    @Provides
    internal fun timeEntriesLogStore(store: Store<TimerState, TimerAction>): Store<TimeEntriesLogState, TimeEntriesLogAction> =
        store.view(
            mapToLocalState = TimeEntriesLogState.Companion::fromTimerState,
            mapToGlobalAction = ::identity
        )
}