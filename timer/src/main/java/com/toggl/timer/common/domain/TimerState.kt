package com.toggl.timer.common.domain

import com.toggl.models.domain.TimeEntry

data class TimerState(val timeEntries: List<TimeEntry>, val localState: LocalState) {
    data class LocalState internal constructor(
        internal val editedDescription: String
    ) {
        constructor() : this("")
    }
}

val TimerState.editedDescription: String
    get() = localState.editedDescription