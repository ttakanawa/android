package com.toggl.architecture.mappings

import com.toggl.api.login.LoginApi
import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.environment.AppEnvironment
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.states.OnboardingState

fun mapAppStateToOnboardingState(appState: AppState): OnboardingState =
    OnboardingState(appState.user, appState.onboardingLocalState)

fun mapAppActionToOnboardingAction(appAction: AppAction): OnboardingAction? =
    if (appAction is AppAction.Onboarding) appAction.onboarding else null

fun mapAppEnvironmentToOnboardingEnvironment(appEnvironment: AppEnvironment): LoginApi =
    appEnvironment.loginApi

fun mapOnboardingStateToAppState(appState: AppState, onboardingState: OnboardingState): AppState =
    appState.copy(user = onboardingState.user, onboardingLocalState = onboardingState.localState)

fun mapOnboardingActionToAppAction(onboardingAction: OnboardingAction): AppAction =
    AppAction.Onboarding(onboardingAction)