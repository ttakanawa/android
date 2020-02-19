package com.toggl.architecture.reducers

import com.toggl.api.login.ILoginApi
import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Effect.Companion.empty
import com.toggl.architecture.core.Reducer

class AppEnvironment(val loginApi: ILoginApi)

val appReducer = Reducer <AppState, AppAction, AppEnvironment> { _, _, _ ->
    empty()
}