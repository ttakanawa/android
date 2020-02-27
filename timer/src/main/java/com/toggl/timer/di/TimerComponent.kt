package com.toggl.timer.di

import com.toggl.timer.ui.TimeEntryLogFragment
import dagger.Subcomponent

@Subcomponent
interface TimerComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): TimerComponent
    }

    fun inject(fragment: TimeEntryLogFragment)
}

interface TimerComponentProvider {
    fun provideTimerComponent(): TimerComponent
}
