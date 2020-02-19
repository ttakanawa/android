package com.toggl.architecture

import com.toggl.architecture.Loadable.Nothing
import com.toggl.models.domain.User
import com.toggl.onboarding.domain.states.OnboardingLocalState

data class AppState(
    val user: Loadable<User>,
    val onboardingLocalState: OnboardingLocalState
) {
    constructor() : this(
        user = Nothing(),
        onboardingLocalState = OnboardingLocalState()
    )
}