package com.toggl.data.db

import androidx.room.*
import com.toggl.models.domain.TimeEntry
import kotlinx.coroutines.flow.Flow


@Dao
interface TimeEntryDao {
    @Query("SELECT * FROM TimeEntry")
    fun getAll(): Flow<List<TimeEntry>>

    @Query("SELECT * FROM TimeEntry WHERE id = :id")
    suspend fun getOne(id: Long): TimeEntry

    @Insert
    fun insertAll(vararg timeEntries: TimeEntry)

    @Update
    fun update(timeEntries: TimeEntry)

    @Delete
    fun delete(user: TimeEntry)
}