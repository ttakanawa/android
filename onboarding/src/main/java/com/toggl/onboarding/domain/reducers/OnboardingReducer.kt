package com.toggl.onboarding.domain.reducers

import com.toggl.api.login.LoginApi
import com.toggl.architecture.Failure
import com.toggl.architecture.Loadable.*
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.models.validation.toEmail
import com.toggl.models.validation.toPassword
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.effects.logUserInEffect
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.onboarding.domain.states.email
import com.toggl.onboarding.domain.states.password

typealias OnboardingReducer = Reducer<OnboardingState, OnboardingAction>
fun createOnboardingReducer(api: LoginApi) = OnboardingReducer { state, action ->

    val currentState = state.value

    when (action) {
        OnboardingAction.LoginTapped -> {
            when (val email = currentState.email) {
                is Email.Invalid -> noEffect()
                is Email.Valid -> when (val password = currentState.password) {
                    is Password.Invalid -> noEffect()
                    is Password.Valid -> {
                        state.value = currentState.copy(user = Loading())
                        logUserInEffect(email, password, api)
                    }
                }
            }
        }
        is OnboardingAction.SetUser -> {
            state.value = currentState.copy(user = Loaded(action.user))
            noEffect()
        }
        is OnboardingAction.SetUserError -> {
            state.value = currentState.copy(user = Error(Failure(action.throwable, "")))
            noEffect()
        }
        is OnboardingAction.EmailEntered -> {
            val newLocalState = currentState.localState.copy(email = action.email.toEmail())
            state.value = currentState.copy(localState = newLocalState)
            noEffect()
        }
        is OnboardingAction.PasswordEntered -> {
            val newLocalState = currentState.localState.copy(password = action.password.toPassword())
            state.value = currentState.copy(localState = newLocalState)
            noEffect()
        }
    }
}