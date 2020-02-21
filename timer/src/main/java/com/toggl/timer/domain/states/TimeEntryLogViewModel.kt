package com.toggl.timer.domain.states

import java.util.*

sealed class TimeEntryLogViewModel

data class FlatTimeEntryItem(
    val id: Long,
    val description: String,
    val startTime: Date,
    val duration: Long?
) : TimeEntryLogViewModel()