package com.toggl.di

import com.toggl.api.login.MockLoginApi
import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.core.Store
import com.toggl.architecture.core.combine
import com.toggl.architecture.mappings.globalOnboardingReducer
import com.toggl.architecture.reducers.AppEnvironment
import com.toggl.architecture.reducers.actionLoggingReducer
import com.toggl.architecture.reducers.appReducer
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.states.OnboardingState
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun appEnvironment(): AppEnvironment =
        AppEnvironment(loginApi = MockLoginApi())

    @Provides
    @Singleton
    fun appStore(environment: AppEnvironment): Store<AppState, AppAction> {

        val combinedReducers = combine(
            actionLoggingReducer,
            appReducer,
            globalOnboardingReducer
        )

        return Store.create(
            initialState = AppState(),
            reducer = combinedReducers,
            environment = environment
        )
    }

    @Provides
    fun onboardingStore(store: Store<AppState, AppAction>): Store<OnboardingState, OnboardingAction> =
        store.view(
            mapToLocalState = {
                OnboardingState(
                    user = it.user,
                    localState = it.onboardingLocalState
                )
            },
            mapToGlobalAction = { AppAction.Onboarding(onboarding = it) }
        )
}