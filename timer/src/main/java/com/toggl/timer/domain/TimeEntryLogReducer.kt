package com.toggl.timer.domain

import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.SimpleReducer
import com.toggl.architecture.core.withoutEffect
import com.toggl.data.db.TimeEntryDao
import javax.inject.Inject

class TimeEntryLogReducer @Inject constructor(private val timeEntryDao: TimeEntryDao) :
    SimpleReducer<TimeEntryLogState, TimeEntryLogAction> {
    override fun reduce(
        state: TimeEntryLogState,
        setState: (TimeEntryLogState.() -> TimeEntryLogState) -> Unit,
        action: TimeEntryLogAction
    ): Effect<TimeEntryLogAction> {
        return when (action) {

            // Data updates
            TimeEntryLogAction.LoadTimeEntryLog -> observeTimeEntries(timeEntryDao)
            is TimeEntryLogAction.TimeEntryLogUpdated -> setState { copy(timeEntries = action.timeEntries) }.withoutEffect()

            // User Triggered Actions
            TimeEntryLogAction.StartTimeEntryButtonTapped -> setState { copy(editedDescription = "") } withEffect startTimeEntry(
                state.editedDescription,
                timeEntryDao
            )
            TimeEntryLogAction.StopTimeEntryButtonTapped -> {
                if (state.runningTimeEntry != null) {
                    stopTimeEntry(
                        state.runningTimeEntry,
                        timeEntryDao
                    )
                } else withoutEffect()
            }
            is TimeEntryLogAction.ContinueButtonTapped -> {
                setState { copy(editedDescription = "") }
                if (state.runningTimeEntry != null) {
                    stopAndContinueTimeEntry(state.runningTimeEntry, action.description, timeEntryDao)
                } else startTimeEntry(action.description, timeEntryDao)
            }
            is TimeEntryLogAction.TimeEntryDescriptionChanged -> setState { copy(editedDescription = action.description) }.withoutEffect()
        }
    }
}