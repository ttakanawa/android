package com.toggl.onboarding.domain.reducers

import com.toggl.api.login.ILoginApi
import com.toggl.architecture.Failure
import com.toggl.architecture.Loadable.Loaded
import com.toggl.architecture.Loadable.Error
import com.toggl.architecture.Loadable.Loading
import com.toggl.architecture.core.Effect
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

val onboardingReducer = Reducer<OnboardingState, OnboardingAction, ILoginApi> { state, action, api ->

    val currentState = state.value

    when (action) {
        OnboardingAction.LoginTapped -> run {
            val validEmail = currentState.email.validEmailOrNull() ?: return@run
            val validPassword = currentState.password.validPasswordOrNull() ?: return@run

            state.value = currentState.copy(user = Loading())

            return@Reducer logUserInEffect(validEmail, validPassword, api)
        }
        is OnboardingAction.SetUser ->
            state.value = currentState.copy(user = Loaded(action.user))
        is OnboardingAction.SetUserError ->
            state.value = currentState.copy(user = Error(Failure(action.throwable, "")))
        is OnboardingAction.EmailEntered -> {
            val newLocalState = currentState.localState.copy(email = action.email.toEmail())
            state.value = currentState.copy(localState = newLocalState)
        }
        is OnboardingAction.PasswordEntered -> {
            val newLocalState = currentState.localState.copy(password = action.password.toPassword())
            state.value = currentState.copy(localState = newLocalState)
        }
    }

    return@Reducer Effect.empty()
}