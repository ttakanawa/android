package com.toggl.timer.domain.states

import com.toggl.models.domain.TimeEntry

data class TimerState(val timeEntries: List<TimeEntry>, val editedDescription: String) {
    fun runningTimeEntryOrNull() = timeEntries.firstOrNull { it.duration == null }
}