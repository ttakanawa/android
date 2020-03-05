package com.toggl.environment.di

import com.toggl.api.login.LoginApi
import com.toggl.api.login.MockLoginApi
import com.toggl.repository.MockRepository
import com.toggl.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EnvironmentModule {

    @Provides
    @Singleton
    fun loginApi() : LoginApi =
        MockLoginApi()

    @Provides
    @Singleton
    fun repository() : Repository =
        MockRepository()
}
