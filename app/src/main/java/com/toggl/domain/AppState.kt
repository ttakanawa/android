package com.toggl.domain

import com.toggl.architecture.Loadable
import com.toggl.models.domain.Project
import com.toggl.models.domain.TimeEntry
import com.toggl.models.domain.User
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.timer.common.domain.TimerState

data class AppState(
    val user: Loadable<User> = Loadable.Uninitialized,
    val timeEntries: Map<Long, TimeEntry> = mapOf(),
    val projects: Map<Long, Project> = mapOf(),
    val onboardingLocalState: OnboardingState.LocalState = OnboardingState.LocalState(),
    val timerLocalState: TimerState.LocalState = TimerState.LocalState()
)
