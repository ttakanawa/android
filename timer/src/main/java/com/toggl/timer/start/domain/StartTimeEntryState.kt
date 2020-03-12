package com.toggl.timer.start.domain

import com.toggl.models.domain.TimeEntry
import com.toggl.timer.common.domain.TimerState
import com.toggl.timer.common.domain.editedDescription

data class StartTimeEntryState(
    val timeEntries: Map<Long, TimeEntry>,
    val editedDescription: String
) {
    companion object {
        fun fromTimerState(timerState: TimerState) =
            StartTimeEntryState(
                timeEntries = timerState.timeEntries,
                editedDescription = timerState.editedDescription
            )

        fun toTimerState(timerState: TimerState, startTimeEntryState: StartTimeEntryState) =
            timerState.copy(
                timeEntries = startTimeEntryState.timeEntries,
                localState = timerState.localState.copy(
                    editedDescription = startTimeEntryState.editedDescription
                )
            )
    }
}
