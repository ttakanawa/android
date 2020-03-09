
package com.toggl.timer.log.domain

import com.toggl.models.domain.Project
import com.toggl.models.domain.TimeEntry
import com.toggl.timer.common.domain.TimerState

data class TimeEntriesLogState(val timeEntries: List<TimeEntry>, val projects: Map<Long, Project>) {
    companion object {
        fun fromTimerState(timerState: TimerState) =
            TimeEntriesLogState(timerState.timeEntries, timerState.projects)

        fun toTimerState(timerState: TimerState, timeEntriesLogState: TimeEntriesLogState) =
            timerState.copy(timeEntries = timeEntriesLogState.timeEntries)
    }
}
