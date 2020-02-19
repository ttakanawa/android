package com.toggl.onboarding.domain.states

import com.toggl.models.validation.Email
import com.toggl.models.validation.Password

data class OnboardingLocalState internal constructor(
    internal val email: Email,
    internal val password: Password
) {
    constructor() : this(Email.Invalid, Password.Invalid)
}