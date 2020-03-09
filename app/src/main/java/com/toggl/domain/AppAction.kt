package com.toggl.domain

import com.toggl.models.domain.TimeEntry
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.timer.common.domain.TimerAction

sealed class AppAction {
    object Load : AppAction()
    data class EntitiesLoaded(val timeEntries: List<TimeEntry>) : AppAction()

    class Onboarding(val onboarding: OnboardingAction) : AppAction()
    class Timer(val timer: TimerAction) : AppAction()
}
