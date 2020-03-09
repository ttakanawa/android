
package com.toggl.models.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class TimeEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val description: String,
    val startTime: Date,
    val duration: Long?,
    val billable: Boolean,
    val projectId: Long?,
    val taskId: Long?
)
