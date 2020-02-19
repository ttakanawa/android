package com.toggl.onboarding.domain.effects

import com.toggl.api.login.ILoginApi
import com.toggl.architecture.core.toEffect
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.onboarding.domain.actions.OnboardingAction

fun logUserInEffect(email: Email.Valid, password: Password.Valid, api: ILoginApi) =
    api.login(email, password)
        .map<OnboardingAction>(OnboardingAction::SetUser)
        .onErrorReturn(OnboardingAction::SetUserError)
        .toEffect()