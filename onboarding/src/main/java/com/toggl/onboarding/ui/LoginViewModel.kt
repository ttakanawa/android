package com.toggl.onboarding.ui

import androidx.lifecycle.ViewModel
import com.toggl.architecture.core.Store
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.onboarding.domain.actions.OnboardingAction

class LoginViewModel(val store: Store<OnboardingState, OnboardingAction>) : ViewModel()
