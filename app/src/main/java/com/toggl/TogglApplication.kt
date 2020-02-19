package com.toggl

import android.app.Application
import com.toggl.di.DaggerAppComponent
import com.toggl.onboarding.di.OnboardingComponent
import com.toggl.onboarding.di.OnboardingComponentProvider

class TogglApplication: Application(), OnboardingComponentProvider {
    
    // Reference to the application graph that is used across the whole app
    val appComponent = DaggerAppComponent.create()

    override fun provideLoginComponent(): OnboardingComponent =
        appComponent.onboardingComponent().create()
}
