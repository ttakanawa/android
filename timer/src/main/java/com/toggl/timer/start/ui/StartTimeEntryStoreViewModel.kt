package com.toggl.timer.start.ui

import androidx.lifecycle.ViewModel
import com.toggl.architecture.core.Store
import com.toggl.timer.start.domain.StartTimeEntryAction
import com.toggl.timer.start.domain.StartTimeEntryState
import javax.inject.Inject

class StartTimeEntryStoreViewModel @Inject constructor(
    store: Store<StartTimeEntryState, StartTimeEntryAction>
) : ViewModel(), Store<StartTimeEntryState, StartTimeEntryAction> by store
