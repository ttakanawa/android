package com.toggl.onboarding.domain.reducers

import com.toggl.api.login.LoginApi
import com.toggl.architecture.Failure
import com.toggl.architecture.Loadable.Loaded
import com.toggl.architecture.Loadable.Error
import com.toggl.architecture.Loadable.Loading
import com.toggl.architecture.core.Reducer
import com.toggl.models.validation.toEmail
import com.toggl.models.validation.toPassword
import com.toggl.models.validation.validEmailOrNull
import com.toggl.models.validation.validPasswordOrNull
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.effects.logUserInEffect
import com.toggl.onboarding.domain.states.email
import com.toggl.onboarding.domain.states.password
import kotlinx.coroutines.flow.emptyFlow

val onboardingReducer = Reducer<OnboardingState, OnboardingAction, LoginApi> { state, action, api ->

    val currentState = state.value

    when (action) {
        OnboardingAction.LoginTapped -> {
            val validEmail = currentState.email.validEmailOrNull() ?: return@Reducer emptyFlow()
            val validPassword = currentState.password.validPasswordOrNull() ?: return@Reducer emptyFlow()

            state.value = currentState.copy(user = Loading())

            logUserInEffect(validEmail, validPassword, api)
        }
        is OnboardingAction.SetUser -> {
            state.value = currentState.copy(user = Loaded(action.user))
            emptyFlow()
        }
        is OnboardingAction.SetUserError -> {
            state.value = currentState.copy(user = Error(Failure(action.throwable, "")))
            emptyFlow()
        }
        is OnboardingAction.EmailEntered -> {
            val newLocalState = currentState.localState.copy(email = action.email.toEmail())
            state.value = currentState.copy(localState = newLocalState)
            emptyFlow()
        }
        is OnboardingAction.PasswordEntered -> {
            val newLocalState = currentState.localState.copy(password = action.password.toPassword())
            state.value = currentState.copy(localState = newLocalState)
            emptyFlow()
        }
    }
}