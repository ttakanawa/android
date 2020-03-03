package com.toggl.timer.domain.actions

import com.toggl.models.domain.TimeEntry

sealed class TimerAction {
    data class TimeEntryStarted(val startedTimeEntry: TimeEntry, val stoppedTimeEntry: TimeEntry?) : TimerAction()
    data class TimeEntryUpdated(val id: Long, val timeEntry: TimeEntry) : TimerAction()
}

sealed class StartTimeEntryAction : TimerAction() {
    object StartTimeEntryButtonTapped : StartTimeEntryAction()
    object StopTimeEntryButtonTapped : StartTimeEntryAction()
    data class TimeEntryDescriptionChanged(val description: String) : StartTimeEntryAction()

    companion object {
        fun fromTimerAction(timerAction: TimerAction) : StartTimeEntryAction? =
            timerAction as? StartTimeEntryAction
    }
}

sealed class TimeEntriesLogAction : TimerAction() {
    data class ContinueButtonTapped(val id: Long) : TimeEntriesLogAction()

    companion object {
        fun fromTimerAction(timerAction: TimerAction): TimeEntriesLogAction? =
            timerAction as? TimeEntriesLogAction
    }
}

fun TimerAction.formatForDebug() : String =
    when(this) {
        StartTimeEntryAction.StartTimeEntryButtonTapped -> "Start time entry button tapped"
        StartTimeEntryAction.StopTimeEntryButtonTapped -> "Stop time entry button tapped"
        is TimeEntriesLogAction.ContinueButtonTapped -> "Continue time entry button tapped for id $id"
        is StartTimeEntryAction.TimeEntryDescriptionChanged -> "Description changed to $description"
        is TimerAction.TimeEntryStarted -> "Time entry started with id $startedTimeEntry.id"
        is TimerAction.TimeEntryUpdated -> "Time entry with id $id updated"
    }