package com.toggl.di

import com.toggl.MainActivity
import com.toggl.environment.di.EnvironmentModule
import com.toggl.onboarding.di.OnboardingComponent
import com.toggl.onboarding.di.OnboardingModule
import com.toggl.timer.di.TimerComponent
import com.toggl.timer.di.TimerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, OnboardingModule::class, TimerModule::class, EnvironmentModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    @Singleton
    fun onboardingComponent(): OnboardingComponent.Factory

    @Singleton
    fun timerComponent(): TimerComponent.Factory

}