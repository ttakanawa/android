package com.toggl.timer.domain.actions

import com.toggl.models.domain.TimeEntry

sealed class TimerAction {
    data class TimeEntryCreated(val timeEntry: TimeEntry) : TimerAction()
    data class TimeEntryUpdated(val id: Long, val timeEntry: TimeEntry) : TimerAction()
}

internal sealed class TimeEntriesLogAction : TimerAction() {
    object StartTimeEntryButtonTapped : TimeEntriesLogAction()
    object StopTimeEntryButtonTapped : TimeEntriesLogAction()
    data class ContinueButtonTapped(val id: Long) : TimeEntriesLogAction()
    data class TimeEntryDescriptionChanged(val description: String) : TimeEntriesLogAction()
}

fun TimerAction.formatForDebug() : String =
    when(this) {
        TimeEntriesLogAction.StartTimeEntryButtonTapped -> "Start time entry button tapped"
        TimeEntriesLogAction.StopTimeEntryButtonTapped -> "Stop time entry button tapped"
        is TimeEntriesLogAction.ContinueButtonTapped -> "Continue time entry button tapped for id $id"
        is TimeEntriesLogAction.TimeEntryDescriptionChanged -> "Description changed to $description"
        is TimerAction.TimeEntryStarted -> "Time entry started with id $startedTimeEntry.id"
        is TimerAction.TimeEntryUpdated -> "Time entry with id $id updated"
    }