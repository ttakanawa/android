package com.toggl.di

import com.toggl.MainActivity
import com.toggl.onboarding.di.OnboardingComponent
import com.toggl.onboarding.di.OnboardingModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, OnboardingModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    @Singleton
    fun onboardingComponent(): OnboardingComponent.Factory

}