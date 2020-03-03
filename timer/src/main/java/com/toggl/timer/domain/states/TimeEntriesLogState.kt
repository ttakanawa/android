package com.toggl.timer.domain.states

data class TimeEntriesLogState(
    val items: List<FlatTimeEntryItem>
) {
    companion object {
        fun fromTimerState(timerState: TimerState) =
            TimeEntriesLogState(
                timerState.timeEntries
                    .filter { it.duration != null }
                    .map { timeEntry ->
                        FlatTimeEntryItem(
                            id = timeEntry.id,
                            description = timeEntry.description,
                            startTime = timeEntry.startTime,
                            duration = timeEntry.duration
                        )
                    }
            )
    }
}