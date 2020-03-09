
package com.toggl.onboarding.di

import com.toggl.onboarding.ui.LoginFragment
import dagger.Subcomponent

@Subcomponent
interface OnboardingComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): OnboardingComponent
    }

    fun inject(fragment: LoginFragment)
}

interface OnboardingComponentProvider {
    fun provideLoginComponent(): OnboardingComponent
}
