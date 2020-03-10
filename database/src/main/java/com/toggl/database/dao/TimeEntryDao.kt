package com.toggl.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.toggl.models.domain.TimeEntry

@Dao
interface TimeEntryDao {
    @Query("SELECT * FROM TimeEntry WHERE NOT isDeleted")
    fun getAll(): List<TimeEntry>

    @Query("SELECT * FROM TimeEntry WHERE NOT isDeleted AND duration is null")
    fun getAllRunning(): List<TimeEntry>

    @Query("SELECT * FROM TimeEntry WHERE NOT isDeleted AND id = :id")
    fun getOne(id: Long): TimeEntry

    @Insert
    fun insertAll(vararg timeEntries: TimeEntry): List<Long>

    @Insert
    fun insert(timeEntries: TimeEntry): Long

    @Update
    fun update(timeEntry: TimeEntry)

    @Update
    fun updateAll(timeEntries: List<TimeEntry>)

    @Delete
    fun delete(user: TimeEntry)
}
