package com.toggl.timer.common

import com.toggl.models.domain.TimeEntry
import org.threeten.bp.OffsetDateTime

fun createTimeEntry(id: Long, description: String) =
    TimeEntry(
        id,
        description,
        OffsetDateTime.now(),
        null,
        false,
        null,
        null
    )