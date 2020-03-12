package com.toggl.environment.di

import android.content.Context
import com.toggl.environment.services.analytics.AnalyticsService
import com.toggl.environment.services.analytics.AnalyticsServiceImpl
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

    @Provides
    @Singleton
    fun analyticsService(context: Context): AnalyticsService =
        AnalyticsServiceImpl(context)
}
