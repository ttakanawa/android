package com.toggl.architecture

import com.toggl.onboarding.domain.actions.OnboardingAction

sealed class AppAction {
    class Onboarding(val onboarding: OnboardingAction) : AppAction()
}