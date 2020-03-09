/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2020, Toggl
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * © 2020 GitHub, Inc.
 */
package com.toggl.onboarding.domain.reducers

import com.toggl.api.login.LoginApi
import com.toggl.architecture.Failure
import com.toggl.architecture.Loadable.Error
import com.toggl.architecture.Loadable.Loaded
import com.toggl.architecture.Loadable.Loading
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.models.validation.toEmail
import com.toggl.models.validation.toPassword
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.effects.logUserInEffect
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.onboarding.domain.states.email
import com.toggl.onboarding.domain.states.password

typealias OnboardingReducer = Reducer<OnboardingState, OnboardingAction>
fun createOnboardingReducer(api: LoginApi) = OnboardingReducer { state, action ->

    val currentState = state.value

    when (action) {
        OnboardingAction.LoginTapped -> {
            when (val email = currentState.email) {
                is Email.Invalid -> noEffect()
                is Email.Valid -> when (val password = currentState.password) {
                    is Password.Invalid -> noEffect()
                    is Password.Valid -> {
                        state.value = currentState.copy(user = Loading())
                        logUserInEffect(email, password, api)
                    }
                }
            }
        }
        is OnboardingAction.SetUser -> {
            state.value = currentState.copy(user = Loaded(action.user))
            noEffect()
        }
        is OnboardingAction.SetUserError -> {
            state.value = currentState.copy(user = Error(Failure(action.throwable, "")))
            noEffect()
        }
        is OnboardingAction.EmailEntered -> {
            val newLocalState = currentState.localState.copy(email = action.email.toEmail())
            state.value = currentState.copy(localState = newLocalState)
            noEffect()
        }
        is OnboardingAction.PasswordEntered -> {
            val newLocalState = currentState.localState.copy(password = action.password.toPassword())
            state.value = currentState.copy(localState = newLocalState)
            noEffect()
        }
    }
}
