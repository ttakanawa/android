package com.toggl.data.di

import android.content.Context
import androidx.room.Room
import com.toggl.data.db.AppDatabase
import com.toggl.data.db.TimeEntryDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    RoomDatabaseModule::class,
    DatabaseDaoModule::class
])
class DatabaseModule

@Module
class RoomDatabaseModule {
    @Provides
    @Singleton
    fun appDatabase(applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "toggl.db"
        ).build()

}

@Module
class DatabaseDaoModule {
    @Provides
    @Singleton
    fun timeEntryDao(appDatabase: AppDatabase): TimeEntryDao = appDatabase.timeEntryDao()
}