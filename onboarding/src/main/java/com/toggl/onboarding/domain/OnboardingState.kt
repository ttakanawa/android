package com.toggl.onboarding.domain

import com.toggl.architecture.Loadable
import com.toggl.models.domain.User
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password

data class OnboardingState(
    val user: Loadable<User>,
    val email: Email,
    val password: Password
)