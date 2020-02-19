package com.toggl.onboarding.domain.coordinators

import androidx.fragment.app.FragmentActivity
import com.toggl.architecture.core.Coordinator
import com.toggl.onboarding.R
import com.toggl.onboarding.ui.LoginFragment

class LoginCoordinator(private val mainCoordinator: Coordinator) : Coordinator() {

    override fun start(activity: FragmentActivity) {

        val fragment = LoginFragment()
        fragment.coordinator = this

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    fun logUserIn(activity: FragmentActivity) {
        mainCoordinator.start(activity)
    }
}