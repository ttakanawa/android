package com.toggl.onboarding.di

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.toggl.architecture.core.Coordinator
import com.toggl.onboarding.domain.coordinators.LoginCoordinator
import dagger.Module
import dagger.Provides

@Module(subcomponents = [OnboardingComponent::class])
class OnboardingModule {
    @Provides
    fun loginCoordinator() : LoginCoordinator =
        LoginCoordinator(
            object : Coordinator() {
                override fun start(activity: FragmentActivity) {
                    Log.i("MockCoordinator", "An attempt to navigate was made")
                }
            }
        )
}