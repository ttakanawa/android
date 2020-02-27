package com.toggl

import android.app.Application
import com.toggl.di.DaggerAppComponent
import com.toggl.timer.di.TimerComponent
import com.toggl.timer.di.TimerComponentProvider

class TogglApplication: Application(), TimerComponentProvider {

    // Reference to the application graph that is used across the whole app
    val appComponent = DaggerAppComponent.factory().create(this)

    override fun provideTimerComponent(): TimerComponent =
        appComponent.timerComponent().create()
}
