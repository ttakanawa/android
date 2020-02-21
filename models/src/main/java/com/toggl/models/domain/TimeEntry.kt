package com.toggl.models.domain

import java.util.*

data class TimeEntry(
    val id: Long,
    val description: String,
    val startTime: Date,
    val duration: Long?
)