package com.toggl.timer.domain.actions

sealed class StartTimeEntryAction {
    object StartTimeEntryButtonTapped : StartTimeEntryAction()
    object StopTimeEntryButtonTapped : StartTimeEntryAction()
    data class TimeEntryDescriptionChanged(val description: String) : StartTimeEntryAction()

    companion object {
        fun fromTimerAction(timerAction: TimerAction) : StartTimeEntryAction? =
                if (timerAction !is TimerAction.StartTimeEntry) null
                else timerAction.startTimeEntryAction

        fun toTimerAction(startTimeEntryAction: StartTimeEntryAction): TimerAction =
            TimerAction.StartTimeEntry(startTimeEntryAction)
    }
}

fun StartTimeEntryAction.formatForDebug() =
    when(this) {
        StartTimeEntryAction.StartTimeEntryButtonTapped -> "Start time entry button tapped"
        StartTimeEntryAction.StopTimeEntryButtonTapped -> "Stop time entry button tapped"
        is StartTimeEntryAction.TimeEntryDescriptionChanged -> "Description changed to $description"
    }