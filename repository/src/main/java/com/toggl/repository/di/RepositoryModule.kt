package com.toggl.repository.di

import com.toggl.database.dao.TimeEntryDao
import com.toggl.environment.services.time.TimeService
import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.repository.timeentry.TimeEntryRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun timeEntryRepository(timeEntryDao: TimeEntryDao, timeService: TimeService): TimeEntryRepository =
        TimeEntryRepositoryImpl(timeEntryDao, timeService)
}
