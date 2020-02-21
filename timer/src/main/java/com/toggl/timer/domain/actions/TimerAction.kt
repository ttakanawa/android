package com.toggl.timer.domain.actions

import com.toggl.models.domain.TimeEntry

sealed class TimerAction {
    class TimeEntryCreated(val timeEntry: TimeEntry) : TimerAction()
    class TimeEntryUpdated(val id: Long, val timeEntry: TimeEntry) : TimerAction()
}

internal sealed class TimeEntriesLogAction : TimerAction() {
    object StartTimeEntryButtonTapped : TimeEntriesLogAction()
    class ContinueButtonTapped(val id: Long) : TimeEntriesLogAction()
}