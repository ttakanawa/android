package com.toggl.architecture.reducers

import android.util.Log
import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.environment.AppEnvironment
import com.toggl.onboarding.domain.actions.formatForDebug
import com.toggl.timer.common.domain.formatForDebug

fun createLoggingReducer() = Reducer <AppState, AppAction, AppEnvironment> { _, action, _ ->

    Log.i("LoggingReducer", when (action) {
        is AppAction.Onboarding -> action.onboarding.formatForDebug()
        is AppAction.Timer -> action.timer.formatForDebug()
    })

    noEffect()
}