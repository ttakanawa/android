package com.toggl.domain

import com.toggl.architecture.Loadable
import com.toggl.models.domain.Project
import com.toggl.models.domain.TimeEntry
import com.toggl.models.domain.User
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.timer.common.domain.TimerState

data class AppState(
    val user: Loadable<User>,
    val timeEntries: List<TimeEntry>,
    val projects: Map<Long, Project>,
    val editedDescription: String,
    val onboardingLocalState: OnboardingState.LocalState,
    val timerLocalState: TimerState.LocalState
) {
    constructor() : this(
        user = Loadable.Uninitialized,
        timeEntries = listOf(),
        projects = mapOf(),
        editedDescription = "",
        onboardingLocalState = OnboardingState.LocalState(),
        timerLocalState = TimerState.LocalState()
    )
}