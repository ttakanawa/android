package com.toggl.architecture

import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.timer.domain.actions.TimerAction

sealed class AppAction {
    class Onboarding(val onboarding: OnboardingAction) : AppAction()
    class Timer(val timer: TimerAction) : AppAction()
}