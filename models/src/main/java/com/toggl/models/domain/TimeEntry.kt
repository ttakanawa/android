package com.toggl.models.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime

@Entity
data class TimeEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val description: String,
    val startTime: OffsetDateTime,
    val duration: Duration?,
    val billable: Boolean,
    val projectId: Long?,
    val taskId: Long?,
    val isDeleted: Boolean
)
