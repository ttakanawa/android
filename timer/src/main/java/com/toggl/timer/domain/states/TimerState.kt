package com.toggl.timer.domain.states

import com.toggl.models.domain.TimeEntry

data class TimerState(val timeEntries: List<TimeEntry>, val localState: LocalState) {
    data class LocalState internal constructor(
        internal val editedDescription: String
    ) {
        constructor() : this("")
    }
}

fun TimerState.runningTimeEntryOrNull() =
    timeEntries.firstOrNull { it.duration == null }

val TimerState.editedDescription: String
    get() = localState.editedDescription