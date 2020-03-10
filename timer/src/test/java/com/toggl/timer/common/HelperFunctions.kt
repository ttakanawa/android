package com.toggl.timer.common

import com.toggl.models.domain.TimeEntry
import java.util.Date

fun createTimeEntry(id: Long, description: String) =
    TimeEntry(
        id,
        description,
        Date(),
        null,
        false,
        null,
        null,
        false
    )