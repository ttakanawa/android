package com.toggl.architecture.reducers

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.environment.AppEnvironment
import kotlinx.coroutines.flow.emptyFlow

val appReducer = Reducer <AppState, AppAction, AppEnvironment> { _, _, _ ->
    emptyFlow()
}