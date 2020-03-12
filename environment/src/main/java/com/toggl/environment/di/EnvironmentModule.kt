package com.toggl.environment.di

import com.toggl.environment.services.time.ThreeTenTimeService
import com.toggl.environment.services.time.TimeService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EnvironmentModule {
    @Provides
    @Singleton
    fun timeService(): TimeService =
        ThreeTenTimeService()
}
