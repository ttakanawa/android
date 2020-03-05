package com.toggl.models.domain

import java.util.*

data class TimeEntry(
    val id: Long,
    val description: String,
    val startTime: Date,
    val duration: Long?,
    val billable: Boolean,
    val projectId: Long?,
    val taskId: Long?,
    val tagIds: List<Long>
)