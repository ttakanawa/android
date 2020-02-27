package com.toggl.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TimeEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val description: String,
    val startTime: Date,
    val duration: Long?
)