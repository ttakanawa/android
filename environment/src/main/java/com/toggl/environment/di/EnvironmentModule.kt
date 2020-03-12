package com.toggl.environment.di

import android.content.Context
import com.toggl.environment.services.analytics.AnalyticsService
import com.toggl.environment.services.analytics.AppCenterAnalyticsService
import com.toggl.environment.services.analytics.CompositeAnalyticsService
import com.toggl.environment.services.analytics.FirebaseAnalyticsService
import com.toggl.environment.services.time.ThreeTenTimeService
import com.toggl.environment.services.time.TimeService
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class EnvironmentModule {
    @Provides
    @Singleton
    fun timeService(): TimeService =
        ThreeTenTimeService()

    @Provides
    @Singleton
    fun analyticsService(
        @Named("FirebaseAnalyticsService") firebaseAnalyticsService: FirebaseAnalyticsService,
        @Named("AppCenterAnalyticsService") appCenterAnalyticsService: AppCenterAnalyticsService
    ): AnalyticsService =
        CompositeAnalyticsService(firebaseAnalyticsService, appCenterAnalyticsService)

    @Provides
    @Singleton
    @Named("FirebaseAnalyticsService")
    fun firebaseAnalyticsService(context: Context) = FirebaseAnalyticsService(context)

    @Provides
    @Singleton
    @Named("AppCenterAnalyticsService")
    fun appCenterAnalyticsService() = AppCenterAnalyticsService()
}
