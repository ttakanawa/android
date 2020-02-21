package com.toggl.timer.di

import com.toggl.timer.ui.TimeEntriesLogFragment
import dagger.Subcomponent

@Subcomponent
interface TimerComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): TimerComponent
    }

    fun inject(fragment: TimeEntriesLogFragment)
}

interface TimerComponentProvider {
    fun provideTimerComponent(): TimerComponent
}
