package com.toggl.timer.log.domain

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.repository.Repository

typealias TimeEntriesLogReducer = Reducer<TimeEntriesLogState, TimeEntriesLogAction, Repository>

internal fun createTimeEntriesLogReducer() =
    TimeEntriesLogReducer { _, action, _ ->
        when (action) {
            is TimeEntriesLogAction.ContinueButtonTapped -> noEffect()
        }
    }