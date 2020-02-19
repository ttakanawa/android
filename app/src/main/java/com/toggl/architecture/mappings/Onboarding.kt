package com.toggl.architecture.mappings

import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.pullback
import com.toggl.architecture.reducers.AppEnvironment
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.onboarding.domain.reducers.onboardingReducer

val globalOnboardingReducer : Reducer<AppState, AppAction, AppEnvironment> =
    pullback(
        reducer = onboardingReducer,
        mapToLocalState = {
            OnboardingState(
                it.user,
                it.onboardingLocalState
            )
        },
        mapToLocalAction = {if (it is AppAction.Onboarding) it.onboarding else null },
        mapToLocalEnvironment = { it.loginApi },
        mapToGlobalAction = { AppAction.Onboarding(it) as AppAction },
        mapToGlobalState = { global: AppState, local ->
            global.copy(
                user = local.user,
                onboardingLocalState =  local.localState
            )
        }
    )