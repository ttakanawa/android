package com.toggl.timer.ui

import androidx.lifecycle.ViewModel
import com.toggl.architecture.core.Store
import com.toggl.timer.domain.actions.StartTimeEntryAction
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.states.StartTimeEntryState
import com.toggl.timer.domain.states.TimeEntriesLogState
import javax.inject.Inject

class StartTimeEntryStoreViewModel @Inject constructor(
    store : Store<StartTimeEntryState, StartTimeEntryAction>
) : ViewModel(), Store<StartTimeEntryState, StartTimeEntryAction> by store