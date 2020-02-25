package com.toggl.timer.domain.states

import com.toggl.models.domain.TimeEntry

data class TimeEntriesLogState(
    val items: List<TimeEntryLogViewModel>,
    val runningTimeEntry: TimeEntry?,
    val editedDescription: String
) {
    companion object {
        fun fromTimerState(timerState: TimerState) =
            TimeEntriesLogState(
                timerState.timeEntries
                    .filter { it.duration != null }
                    .map { timeEntry ->
                        FlatTimeEntryItem(
                            id = timeEntry.id,
                            description = timeEntry.description,
                            startTime = timeEntry.startTime,
                            duration = timeEntry.duration
                        )
                    },
                timerState.timeEntries.firstOrNull { it.duration == null },
                timerState.editedDescription
            )
    }
}