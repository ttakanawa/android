package com.toggl.architecture.reducers

import android.util.Log
import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Effect.Companion.empty
import com.toggl.architecture.core.Reducer
import com.toggl.onboarding.domain.actions.OnboardingAction


val actionLoggingReducer = Reducer <AppState, AppAction, AppEnvironment> { _, action, _ ->

    val tag = "LoggingReducer"
    when(action) {
        is AppAction.Onboarding -> {
            when(action.onboarding) {
                OnboardingAction.LoginTapped -> Log.i(tag, "LoginTapped")
                is OnboardingAction.SetUser -> Log.i(tag, "User Set")
                is OnboardingAction.SetUserError -> Log.i(tag, "User Error")
                is OnboardingAction.EmailEntered -> Log.i(tag, "Email Entered: " + action.onboarding.email)
                is OnboardingAction.PasswordEntered -> Log.i(tag, "Password Entered: " + action.onboarding.password)
            }
        }
    }

    empty()
}