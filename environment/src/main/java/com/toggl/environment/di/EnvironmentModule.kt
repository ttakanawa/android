package com.toggl.environment.di

import com.toggl.api.login.LoginApi
import com.toggl.api.login.MockLoginApi
import com.toggl.database.dao.TimeEntryDao
import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.repository.timeentry.TimeEntryRepositoryImpl
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
    fun timeEntryRepository(timeEntryDao: TimeEntryDao) : TimeEntryRepository =
        TimeEntryRepositoryImpl(timeEntryDao)
}
