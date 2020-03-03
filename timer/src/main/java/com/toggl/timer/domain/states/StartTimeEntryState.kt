package com.toggl.timer.domain.states

import com.toggl.models.domain.TimeEntry

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