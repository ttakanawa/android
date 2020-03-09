
package com.toggl.timer.common.domain

import com.toggl.timer.log.domain.TimeEntriesLogAction
import com.toggl.timer.log.domain.formatForDebug
import com.toggl.timer.start.domain.StartTimeEntryAction
import com.toggl.timer.start.domain.formatForDebug

sealed class TimerAction {
    class StartTimeEntry(val startTimeEntryAction: StartTimeEntryAction) : TimerAction()
    class TimeEntriesLog(val timeEntriesLogAction: TimeEntriesLogAction) : TimerAction()
}

fun TimerAction.formatForDebug(): String =
    when (this) {
        is TimerAction.StartTimeEntry -> startTimeEntryAction.formatForDebug()
        is TimerAction.TimeEntriesLog -> timeEntriesLogAction.formatForDebug()
    }
