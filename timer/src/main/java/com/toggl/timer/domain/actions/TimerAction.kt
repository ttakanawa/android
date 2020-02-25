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