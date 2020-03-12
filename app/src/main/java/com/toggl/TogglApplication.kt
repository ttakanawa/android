package com.toggl

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.toggl.di.DaggerAppComponent
import com.toggl.onboarding.di.OnboardingComponent
import com.toggl.onboarding.di.OnboardingComponentProvider
import com.toggl.timer.di.TimerComponent
import com.toggl.timer.di.TimerComponentProvider

class TogglApplication : Application(), OnboardingComponentProvider, TimerComponentProvider {

    // Reference to the application graph that is used across the whole app
    val appComponent = DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        if (!BuildConfig.DEBUG) {
            AppCenter.start(this, "TheAppSecret", Analytics::class.java)
        }
    }

    override fun provideLoginComponent(): OnboardingComponent =
        appComponent.onboardingComponent().create()

    override fun provideTimerComponent(): TimerComponent =
        appComponent.timerComponent().create()
}
