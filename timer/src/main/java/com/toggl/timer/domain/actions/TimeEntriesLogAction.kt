package com.toggl.timer.domain.actions

sealed class TimeEntriesLogAction {
    data class ContinueButtonTapped(val id: Long) : TimeEntriesLogAction()

    companion object {
        fun toTimerAction(timeEntriesLogAction: TimeEntriesLogAction) =
            TimerAction.TimeEntriesLog(timeEntriesLogAction)

        fun fromTimerAction(timerAction: TimerAction): TimeEntriesLogAction? =
            if (timerAction !is TimerAction.TimeEntriesLog) null
            else timerAction.timeEntriesLogAction
    }
}

fun TimeEntriesLogAction.formatForDebug() =
    when(this) {
        is TimeEntriesLogAction.ContinueButtonTapped -> "Continue time entry button tapped for id $id"
    }