package com.toggl.onboarding.domain.states

import com.toggl.architecture.Loadable
import com.toggl.models.domain.User
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password

data class OnboardingState(
    val user: Loadable<User>,
    val localState: LocalState
) {
    data class LocalState internal constructor(
        internal val email: Email,
        internal val password: Password
    ) {
        constructor() : this(Email.Invalid(""), Password.Invalid(""))
    }
}

internal val OnboardingState.email: Email
    get() = localState.email

internal val OnboardingState.password: Password
    get() = localState.password