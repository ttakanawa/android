package com.toggl.timer.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import com.toggl.data.db.TimeEntryDao
import com.toggl.timer.domain.TimeEntryLogReducer
import dagger.Module
import dagger.Provides

@AssistedModule
@Module(subcomponents = [TimerComponent::class], includes = [AssistedInject_TimerModule::class])
class TimerModule {
    @Provides
    internal fun timeEntryReducer(timeEntryDao: TimeEntryDao): TimeEntryLogReducer = TimeEntryLogReducer(timeEntryDao)
}