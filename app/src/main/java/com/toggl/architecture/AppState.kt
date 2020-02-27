package com.toggl.architecture

import com.toggl.architecture.Loadable.Nothing
import com.toggl.models.domain.TimeEntry
import com.toggl.models.domain.User
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.timer.domain.states.TimerState

data class AppState(
    val user: Loadable<User>,
    val timeEntries: List<TimeEntry>,
    val editedDescription: String,
    val onboardingLocalState: OnboardingState.LocalState,
    val timerLocalState: TimerState.LocalState
) {
    constructor() : this(
        user = Nothing(),
        timeEntries = listOf(),
        editedDescription = "",
        onboardingLocalState = OnboardingState.LocalState(),
        timerLocalState = TimerState.LocalState()
    )
}