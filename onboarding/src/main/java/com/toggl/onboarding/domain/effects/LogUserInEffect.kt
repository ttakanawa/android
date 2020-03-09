package com.toggl.onboarding.domain.effects

import com.toggl.api.login.LoginApi
import com.toggl.architecture.core.Effect
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.onboarding.domain.actions.OnboardingAction
import kotlinx.coroutines.flow.flow

fun logUserInEffect(
    email: Email.Valid,
    password: Password.Valid,
    api: LoginApi
): Effect<OnboardingAction> =
    flow {
        try {
            val user = api.login(email, password)
            val userAction = OnboardingAction.SetUser(user)
            emit(userAction)
        } catch (throwable: Throwable) {
            val errorAction = OnboardingAction.SetUserError(throwable)
            emit(errorAction)
        }
    }
