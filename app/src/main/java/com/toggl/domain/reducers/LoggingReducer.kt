package com.toggl.domain.reducers

import android.util.Log
import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.onboarding.domain.actions.formatForDebug
import com.toggl.timer.common.domain.formatForDebug

fun createLoggingReducer() = Reducer <AppState, AppAction> { _, action ->

    Log.i("LoggingReducer", when (action) {
        is AppAction.Onboarding -> action.onboarding.formatForDebug()
        is AppAction.Timer -> action.timer.formatForDebug()
        AppAction.Load -> "Initial load of entities"
        is AppAction.EntitiesLoaded -> "Entities loaded"
    })

    noEffect()
}