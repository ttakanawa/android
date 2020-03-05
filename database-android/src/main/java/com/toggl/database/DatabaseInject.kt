package com.toggl.database

import android.content.Context
import androidx.room.Room
import com.toggl.database.dao.TimeEntryDao
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
    fun appDatabase(applicationContext: Context): TogglDatabase =
        Room.databaseBuilder(
            applicationContext,
            TogglRoomDatabase::class.java, "toggl.db"
        ).build()
}

@Module
class DatabaseDaoModule {
    @Provides
    @Singleton
    fun timeEntryDao(appDatabase: TogglDatabase): TimeEntryDao = appDatabase.timeEntryDao()
}