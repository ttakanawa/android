package com.toggl.timer.domain.actions

sealed class TimeEntriesLogAction {
    data class ContinueButtonTapped(val id: Long) : TimeEntriesLogAction()

    companion object {
        fun fromTimerAction(timerAction: TimerAction): TimeEntriesLogAction? =
            if (timerAction !is TimerAction.TimeEntriesLog) null
            else timerAction.timeEntriesLogAction

        fun toTimerAction(timeEntriesLogAction: TimeEntriesLogAction): TimerAction =
            TimerAction.TimeEntriesLog(timeEntriesLogAction)
    }
}

fun TimeEntriesLogAction.formatForDebug() =
    when(this) {
        is TimeEntriesLogAction.ContinueButtonTapped -> "Continue time entry button tapped for id $id"
    }