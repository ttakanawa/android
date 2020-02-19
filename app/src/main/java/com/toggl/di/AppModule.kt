package com.toggl.di

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.toggl.api.login.ILoginApi
import com.toggl.api.login.MockLoginApi
import com.toggl.architecture.AppAction
import com.toggl.architecture.AppState
import com.toggl.architecture.coordinators.AuthCoordinator
import com.toggl.architecture.core.*
import com.toggl.architecture.mappings.globalOnboardingReducer
import com.toggl.architecture.reducers.AppEnvironment
import com.toggl.architecture.reducers.actionLoggingReducer
import com.toggl.architecture.reducers.appReducer
import com.toggl.models.validation.Password
import com.toggl.onboarding.domain.OnboardingState
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.coordinators.LoginCoordinator
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
                    email = it.email,
                    password = it.password
                )
            },
            mapToGlobalAction = { AppAction.Onboarding(onboarding = it) }
        )

    @Provides
    fun authCoordinator(loginCoordinator: LoginCoordinator): AuthCoordinator {

        val mockCoordinator = object : Coordinator() {
            override fun start(activity: FragmentActivity) {
                Log.i("MockCoordinator", "An attempt to navigate was made")
            }
        }

        return AuthCoordinator(
            loginCoordinator,
            mockCoordinator
        )
    }
}