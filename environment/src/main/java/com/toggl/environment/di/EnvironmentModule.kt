package com.toggl.environment.di

import com.toggl.environment.services.ThreeTenTimeService
import com.toggl.environment.services.TimeService
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
