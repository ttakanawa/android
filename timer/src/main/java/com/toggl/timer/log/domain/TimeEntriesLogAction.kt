package com.toggl.timer.log.domain

import com.toggl.models.domain.TimeEntry
import com.toggl.timer.common.domain.TimerAction

sealed class TimeEntriesLogAction {
    data class ContinueButtonTapped(val id: Long) : TimeEntriesLogAction()
    data class TimeEntryStarted(val startedTimeEntry: TimeEntry, val stoppedTimeEntry: TimeEntry?) : TimeEntriesLogAction()

    companion object {
        fun fromTimerAction(timerAction: TimerAction): TimeEntriesLogAction? =
            if (timerAction !is TimerAction.TimeEntriesLog) null
            else timerAction.timeEntriesLogAction

        fun toTimerAction(timeEntriesLogAction: TimeEntriesLogAction): TimerAction =
            TimerAction.TimeEntriesLog(
                timeEntriesLogAction
            )
    }
}

fun TimeEntriesLogAction.formatForDebug() =
    when(this) {
        is TimeEntriesLogAction.ContinueButtonTapped -> "Continue time entry button tapped for id $id"
        is TimeEntriesLogAction.TimeEntryStarted -> "Time entry started with id $startedTimeEntry.id"
    }