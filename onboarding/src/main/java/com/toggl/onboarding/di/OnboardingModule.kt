package com.toggl.onboarding.di

import com.toggl.onboarding.domain.reducers.OnboardingReducer
import com.toggl.onboarding.domain.reducers.createOnboardingReducer
import dagger.Module
import dagger.Provides

@Module(subcomponents = [OnboardingComponent::class])
class OnboardingModule {
    @Provides
    fun onboardingReducer() : OnboardingReducer =
        createOnboardingReducer()
}