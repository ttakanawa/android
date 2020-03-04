package com.toggl.timer.start.domain

import com.toggl.models.domain.TimeEntry
import com.toggl.timer.common.domain.TimerState
import com.toggl.timer.common.domain.editedDescription
import com.toggl.timer.common.domain.runningTimeEntryOrNull

data class StartTimeEntryState(
    val runningTimeEntry: TimeEntry?,
    val editedDescription: String
) {
    companion object {
        fun fromTimerState(timerState: TimerState) =
            StartTimeEntryState(
                timerState.runningTimeEntryOrNull(),
                timerState.editedDescription
            )

        fun toTimerState(timerState: TimerState, startTimeEntryState: StartTimeEntryState) =
            timerState.copy(
                localState = timerState.localState.copy(
                    editedDescription = startTimeEntryState.editedDescription
                )
            )
    }
}