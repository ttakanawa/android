package com.toggl.architecture.reducers

import android.util.Log
import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Effect.Companion.empty
import com.toggl.architecture.core.Reducer
import com.toggl.environment.AppEnvironment


val actionLoggingReducer = Reducer <AppState, AppAction, AppEnvironment> { _, action, _ ->
    val tag = "LoggingReducer"
    Log.i(tag, action.toString())
    empty()
}