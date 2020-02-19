package com.toggl.onboarding.domain.reducers

import com.toggl.api.login.ILoginApi
import com.toggl.architecture.Loadable.Error
import com.toggl.architecture.Loadable.Loaded
import com.toggl.architecture.Loadable.Loading
import com.toggl.architecture.core.Effect
import com.toggl.architecture.core.Reducer
import com.toggl.models.validation.toEmail
import com.toggl.models.validation.toPassword
import com.toggl.models.validation.validEmailOrNull
import com.toggl.models.validation.validPasswordOrNull
import com.toggl.onboarding.domain.OnboardingState
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.effects.logUserInEffect

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
            state.value = currentState.copy(user = Error(action.throwable))
        is OnboardingAction.EmailEntered ->
            state.value = currentState.copy(email = action.email.toEmail())
        is OnboardingAction.PasswordEntered ->
            state.value = currentState.copy(password = action.password.toPassword())
    }

    return@Reducer Effect.empty()
}