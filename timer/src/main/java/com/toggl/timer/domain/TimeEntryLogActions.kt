package com.toggl.timer.domain

import com.toggl.models.domain.TimeEntry


sealed class TimeEntryLogAction {
    object LoadTimeEntryLog : TimeEntryLogAction()
    object StartTimeEntryButtonTapped : TimeEntryLogAction()
    object StopTimeEntryButtonTapped : TimeEntryLogAction()
    data class TimeEntryLogUpdated(val timeEntries: List<TimeEntry>) : TimeEntryLogAction()
    data class ContinueButtonTapped(val description: String) : TimeEntryLogAction()
    data class TimeEntryDescriptionChanged(val description: String) : TimeEntryLogAction()
}