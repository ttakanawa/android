package com.toggl.environment.di

import com.toggl.api.login.MockLoginApi
import com.toggl.repository.MockRepository
import com.toggl.environment.AppEnvironment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EnvironmentModule {

    @Provides
    @Singleton
    fun appEnvironment(): AppEnvironment =
        AppEnvironment(
            MockLoginApi(),
            MockRepository()
        )
}
