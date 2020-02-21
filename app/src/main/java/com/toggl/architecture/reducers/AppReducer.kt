package com.toggl.architecture.reducers

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Effect.Companion.empty
import com.toggl.architecture.core.Reducer
import com.toggl.environment.AppEnvironment

val appReducer = Reducer <AppState, AppAction, AppEnvironment> { _, _, _ ->
    empty()
}