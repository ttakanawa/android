package com.toggl.onboarding.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.lifecycle.toLiveData
import androidx.navigation.fragment.findNavController
import com.toggl.architecture.Loadable
import com.toggl.architecture.core.Store
import com.toggl.common.DeepLinkUrls
import com.toggl.models.domain.User
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.onboarding.R
import com.toggl.onboarding.di.OnboardingComponentProvider
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.onboarding.domain.states.email
import com.toggl.onboarding.domain.states.password
import io.reactivex.rxjava3.core.BackpressureStrategy
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.login_fragment) {

    @Inject lateinit var store : Store<OnboardingState, OnboardingAction>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().applicationContext as OnboardingComponentProvider)
            .provideLoginComponent().inject(this)

        loginButton.setOnClickListener {
            store.dispatch(OnboardingAction.LoginTapped)
        }

        emailEditText.doOnTextChanged { text, _, _, _ ->
            val action = OnboardingAction.EmailEntered(text.toString())
            store.dispatch(action)
        }

        passwordEditText.doOnTextChanged { text, _, _, _ ->
            val action = OnboardingAction.PasswordEntered(text.toString())
            store.dispatch(action)
        }

        store.state
            .map { it.email is Email.Valid && it.password is Password.Valid && it.user !is Loadable.Loading }
            .distinctUntilChanged()
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
            .observe(this, loginButton::setEnabled)

        store.state.map { it.user }
            .filter { it is Loadable.Loaded<User> }
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
            .observe(this) {
                findNavController().navigate(DeepLinkUrls.timeEntryLog)
            }
    }
}
