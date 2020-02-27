package com.toggl.timer.domain

import com.airbnb.mvrx.MvRxState
import com.toggl.models.domain.TimeEntry
import java.util.*

data class TimeEntryLogState(private val timeEntries: List<TimeEntry> = listOf(), val editedDescription: String = ""): MvRxState {
    val items: List<TimeEntryLogViewModel> = timeEntries
        .filter { it.duration != null }
        .map { timeEntry ->
            FlatTimeEntryItem(
                id = timeEntry.id,
                description = timeEntry.description,
                startTime = timeEntry.startTime,
                duration = timeEntry.duration
            )
        }
    val runningTimeEntry: TimeEntry? = timeEntries.firstOrNull { it.duration == null }
    val anyRunningTimeEntry: Boolean = runningTimeEntry != null
}

sealed class TimeEntryLogViewModel

data class FlatTimeEntryItem(
    val id: Long,
    val description: String,
    val startTime: Date,
    val duration: Long?
) : TimeEntryLogViewModel()

object FeedbackItem: TimeEntryLogViewModel()