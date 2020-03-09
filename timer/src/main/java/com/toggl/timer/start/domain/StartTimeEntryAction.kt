package com.toggl.timer.start.domain

import com.toggl.models.domain.TimeEntry
import com.toggl.timer.common.domain.TimerAction

sealed class StartTimeEntryAction {
    object StartTimeEntryButtonTapped : StartTimeEntryAction()
    object StopTimeEntryButtonTapped : StartTimeEntryAction()
    data class TimeEntryDescriptionChanged(val description: String) : StartTimeEntryAction()
    data class TimeEntryUpdated(val id: Long, val timeEntry: TimeEntry) : StartTimeEntryAction()
    data class TimeEntryStarted(val startedTimeEntry: TimeEntry, val stoppedTimeEntry: TimeEntry?) :
        StartTimeEntryAction()

    companion object {
        fun fromTimerAction(timerAction: TimerAction): StartTimeEntryAction? =
            if (timerAction !is TimerAction.StartTimeEntry) null
            else timerAction.startTimeEntryAction

        fun toTimerAction(startTimeEntryAction: StartTimeEntryAction): TimerAction =
            TimerAction.StartTimeEntry(
                startTimeEntryAction
            )
    }
}

fun StartTimeEntryAction.formatForDebug() =
    when (this) {
        StartTimeEntryAction.StartTimeEntryButtonTapped -> "Start time entry button tapped"
        StartTimeEntryAction.StopTimeEntryButtonTapped -> "Stop time entry button tapped"
        is StartTimeEntryAction.TimeEntryDescriptionChanged -> "Description changed to $description"
        is StartTimeEntryAction.TimeEntryUpdated -> "Time entry with id $id updated"
        is StartTimeEntryAction.TimeEntryStarted -> "Time entry started with id $startedTimeEntry.id"
    }
