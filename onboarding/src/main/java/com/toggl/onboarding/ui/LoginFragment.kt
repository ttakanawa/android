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
package com.toggl.onboarding.ui

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.toggl.architecture.Loadable
import com.toggl.architecture.extensions.emitIf
import com.toggl.architecture.isLoaded
import com.toggl.common.DeepLinkUrls
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.onboarding.R
import com.toggl.onboarding.di.OnboardingComponentProvider
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.states.email
import com.toggl.onboarding.domain.states.password
import javax.inject.Inject
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.login_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val store: LoginViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as OnboardingComponentProvider)
            .provideLoginComponent().inject(this)
        super.onAttach(context)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {
            store.dispatch(OnboardingAction.LoginTapped)
        }

        email.doOnTextChanged { text, _, _, _ ->
            val action = OnboardingAction.EmailEntered(text.toString())
            store.dispatch(action)
        }

        password.doOnTextChanged { text, _, _, _ ->
            val action = OnboardingAction.PasswordEntered(text.toString())
            store.dispatch(action)
        }

        lifecycleScope.launch {
            store.state
                .emitIf { it.user.isLoaded() }
                .onEach {
                    view.windowToken?.let(::dismissKeyboard)
                    findNavController().navigate(DeepLinkUrls.main)
                }.launchIn(this)

            store.state
                .map { it.email is Email.Valid && it.password is Password.Valid && it.user !is Loadable.Loading }
                .distinctUntilChanged()
                .onEach { login_button.isEnabled = it }
                .launchIn(this)
        }
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}
