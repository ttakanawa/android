package com.toggl.di

import com.toggl.TogglApplication
import com.toggl.database.DatabaseModule
import com.toggl.environment.di.EnvironmentModule
import com.toggl.onboarding.di.OnboardingComponent
import com.toggl.onboarding.di.OnboardingModule
import com.toggl.repository.di.RepositoryModule
import com.toggl.timer.di.TimerComponent
import com.toggl.timer.di.TimerModule
import com.toggl.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        OnboardingModule::class,
        TimerModule::class,
        EnvironmentModule::class,
        DatabaseModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: TogglApplication): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(togglApplication: TogglApplication)

    @Singleton
    fun onboardingComponent(): OnboardingComponent.Factory

    @Singleton
    fun timerComponent(): TimerComponent.Factory
}
