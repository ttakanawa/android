package com.toggl.onboarding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.toggl.api.login.ILoginApi
import com.toggl.architecture.Loadable
import com.toggl.architecture.core.IStore
import com.toggl.architecture.extensions.addTo
import com.toggl.models.domain.User
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.onboarding.domain.OnboardingState
import com.toggl.onboarding.R
import com.toggl.onboarding.di.OnboardingComponentProvider
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.coordinators.LoginCoordinator
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    lateinit var coordinator: LoginCoordinator
    private val disposeBag = CompositeDisposable()
    @Inject lateinit var store : IStore<OnboardingState, OnboardingAction, ILoginApi>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.login_fragment, container, false)


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
            .subscribe(loginButton::setEnabled)
            .addTo(disposeBag)

        store.state.map { it.user }
            .filter { it is Loadable.Loaded<User> }
            .subscribe { coordinator.logUserIn(requireActivity()) }
            .addTo(disposeBag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposeBag.dispose()
    }
}
