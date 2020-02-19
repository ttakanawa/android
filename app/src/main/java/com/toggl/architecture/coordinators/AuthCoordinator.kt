package com.toggl.architecture.coordinators

import androidx.fragment.app.FragmentActivity
import com.toggl.architecture.core.Coordinator

class AuthCoordinator(
    private val loginCoordinator: Coordinator,
    private val mainCoordinator: Coordinator
) : Coordinator() {

    override fun start(activity: FragmentActivity) {

        val userIsLoggedIn = checkIfUserIsLoggedIn()
        if (userIsLoggedIn) {
            mainCoordinator.start(activity)
        } else {
            loginCoordinator.start(activity)
        }
    }

    private fun checkIfUserIsLoggedIn() = false

}