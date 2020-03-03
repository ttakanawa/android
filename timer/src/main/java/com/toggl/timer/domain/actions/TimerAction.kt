package com.toggl.timer.domain.actions

import com.toggl.models.domain.TimeEntry

sealed class TimerAction {
    class StartTimeEntry(val startTimeEntryAction: StartTimeEntryAction) : TimerAction()
    class TimeEntriesLog(val timeEntriesLogAction: TimeEntriesLogAction) : TimerAction()

    data class TimeEntryUpdated(val id: Long, val timeEntry: TimeEntry) : TimerAction()
    data class TimeEntryStarted(val startedTimeEntry: TimeEntry, val stoppedTimeEntry: TimeEntry?) : TimerAction()
}

fun TimerAction.formatForDebug() : String =
    when(this) {
        is TimerAction.StartTimeEntry -> startTimeEntryAction.formatForDebug()
        is TimerAction.TimeEntriesLog -> timeEntriesLogAction.formatForDebug()
        is TimerAction.TimeEntryStarted -> "Time entry started with id $startedTimeEntry.id"
        is TimerAction.TimeEntryUpdated -> "Time entry with id $id updated"
    }