package com.toggl.timer.ui

import androidx.lifecycle.ViewModel
import com.toggl.architecture.core.Store
import com.toggl.timer.domain.actions.TimeEntriesLogAction
import com.toggl.timer.domain.states.TimeEntriesLogState
import javax.inject.Inject

class TimeEntriesLogStoreViewModel @Inject constructor(
    store : Store<TimeEntriesLogState, TimeEntriesLogAction>
) : ViewModel(), Store<TimeEntriesLogState, TimeEntriesLogAction> by store