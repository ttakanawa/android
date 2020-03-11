package com.toggl.onboarding.di

import com.toggl.api.login.LoginApi
import com.toggl.api.login.MockLoginApi
import com.toggl.onboarding.domain.reducers.OnboardingReducer
import com.toggl.onboarding.domain.reducers.createOnboardingReducer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [OnboardingComponent::class])
class OnboardingModule {
    @Provides
    fun onboardingReducer(loginApi: LoginApi): OnboardingReducer =
        createOnboardingReducer(loginApi)

    @Provides
    @Singleton
    fun loginApi(): LoginApi =
        MockLoginApi()
}
