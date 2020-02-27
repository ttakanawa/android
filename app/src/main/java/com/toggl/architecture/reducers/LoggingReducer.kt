package com.toggl.architecture.reducers

import android.util.Log
import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.environment.AppEnvironment
import kotlinx.coroutines.flow.emptyFlow


val actionLoggingReducer = Reducer <AppState, AppAction, AppEnvironment> { _, action, _ ->
    val tag = "LoggingReducer"
    Log.i(tag, action.toString())
    emptyFlow()
}