package com.toggl.onboarding.ui

import androidx.lifecycle.ViewModel
import com.toggl.api.login.ILoginApi
import com.toggl.architecture.core.Store
import com.toggl.onboarding.domain.OnboardingState
import com.toggl.onboarding.domain.actions.OnboardingAction

class LoginViewModel(val store: Store<OnboardingState, OnboardingAction>) : ViewModel()
