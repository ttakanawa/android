package com.toggl.domain.mappings

import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.states.OnboardingState

fun mapAppStateToOnboardingState(appState: AppState): OnboardingState =
    OnboardingState(appState.user, appState.onboardingLocalState)

fun mapAppActionToOnboardingAction(appAction: AppAction): OnboardingAction? =
    if (appAction is AppAction.Onboarding) appAction.onboarding else null

fun mapOnboardingStateToAppState(appState: AppState, onboardingState: OnboardingState): AppState =
    appState.copy(user = onboardingState.user, onboardingLocalState = onboardingState.localState)

fun mapOnboardingActionToAppAction(onboardingAction: OnboardingAction): AppAction =
    AppAction.Onboarding(onboardingAction)
