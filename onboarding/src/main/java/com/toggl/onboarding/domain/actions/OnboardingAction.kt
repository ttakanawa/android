package com.toggl.onboarding.domain.actions

import com.toggl.models.domain.User

sealed class OnboardingAction {
    object LoginTapped : OnboardingAction()
    class SetUser(val user: User) : OnboardingAction()
    class SetUserError(val throwable: Throwable) : OnboardingAction()
    class EmailEntered(val email: String) : OnboardingAction()
    class PasswordEntered(val password: String) : OnboardingAction()
}