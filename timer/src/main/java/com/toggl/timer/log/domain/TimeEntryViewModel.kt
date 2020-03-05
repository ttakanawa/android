package com.toggl.timer.log.domain

import java.util.*

sealed class TimeEntryViewModel

data class FlatTimeEntryItem(
    val id: Long,
    val description: String,
    val startTime: Date,
    val duration: Long?,
    val project: ProjectViewModel?,
    val billable: Boolean
) : TimeEntryViewModel()


data class ProjectViewModel(
    val id: Long,
    val name: String,
    val color: String
)