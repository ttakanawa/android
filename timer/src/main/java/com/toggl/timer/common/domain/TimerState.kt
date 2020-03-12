package com.toggl.timer.common.domain

import com.toggl.models.domain.Project
import com.toggl.models.domain.TimeEntry

data class TimerState(
    val timeEntries: Map<Long, TimeEntry>,
    val projects: Map<Long, Project>,
    val localState: LocalState
) {
    data class LocalState internal constructor(
        internal val editedDescription: String,
        internal val editedTimeEntry: TimeEntry?
    ) {
        constructor() : this("", null)
    }
}

val TimerState.editedDescription: String
    get() = localState.editedDescription
