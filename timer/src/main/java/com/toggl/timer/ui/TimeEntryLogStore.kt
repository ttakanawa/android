package com.toggl.timer.ui

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.toggl.architecture.core.StoreViewModel
import com.toggl.timer.domain.TimeEntryLogAction
import com.toggl.timer.domain.TimeEntryLogReducer
import com.toggl.timer.domain.TimeEntryLogState

class TimeEntryLogStore @AssistedInject constructor(
    @Assisted initialState: TimeEntryLogState,
    timeEntryLogReducer: TimeEntryLogReducer
) : StoreViewModel<TimeEntryLogAction, TimeEntryLogState>(initialState, timeEntryLogReducer) {

    init {
        dispatch(TimeEntryLogAction.LoadTimeEntryLog)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: TimeEntryLogState): TimeEntryLogStore
    }

    companion object : MvRxViewModelFactory<TimeEntryLogStore, TimeEntryLogState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: TimeEntryLogState
        ): TimeEntryLogStore? {
            val fragment: TimeEntryLogFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.loginStoreFactory.create(state)
        }
    }
}

