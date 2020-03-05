package com.toggl.timer.log.domain

import com.toggl.models.domain.TimeEntry
import com.toggl.timer.common.domain.TimerState

data class TimeEntriesLogState(val timeEntries: List<TimeEntry>) {
    companion object {
        fun fromTimerState(timerState: TimerState) =
            TimeEntriesLogState(timerState.timeEntries)

        fun toTimerState(timerState: TimerState, timeEntriesLogState: TimeEntriesLogState) =
            timerState.copy(timeEntries = timeEntriesLogState.timeEntries)
    }
}