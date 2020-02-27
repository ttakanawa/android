package com.toggl.di

import com.toggl.TogglApplication
import com.toggl.data.db.AppDatabase
import com.toggl.data.db.TimeEntryDao
import com.toggl.data.di.DatabaseModule
import com.toggl.environment.di.EnvironmentModule
import com.toggl.timer.di.TimerComponent
import com.toggl.timer.di.TimerModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, EnvironmentModule::class, TimerModule::class, DatabaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: TogglApplication): AppComponent
    }

    @Singleton
    fun timerComponent(): TimerComponent.Factory

    @Singleton
    fun database(): AppDatabase

    @Singleton
    fun timeEntryDao(): TimeEntryDao
}