package com.toggl.onboarding.reducers

import com.toggl.api.login.LoginApi
import com.toggl.architecture.Failure
import com.toggl.architecture.Loadable
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.SettableValue
import com.toggl.models.domain.User
import com.toggl.models.validation.ApiToken
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.reducers.onboardingReducer
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.onboarding.domain.states.email
import com.toggl.onboarding.domain.states.password
import org.amshove.kluent.mock
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.junit.Test


abstract class BaseReducerTest<State, Action, Environment>(val reducer: Reducer<State, Action, Environment>) {
    abstract fun emptyState() : State

    fun State.toSettableValue(setFunction: (State) -> Unit) =
        SettableValue({ this }, setFunction)
}

abstract class TheOnboardingReducer
    : BaseReducerTest<OnboardingState, OnboardingAction, LoginApi>(onboardingReducer) {

    val environment = mock(LoginApi::class)

    companion object {
        val validPassword = Password.from("avalidpassword")
        val validEmail = Email.from("validemail@toggl.com")
        val validUser = User(
            ApiToken.from("12345678901234567890123456789012")
        )
    }

    override fun emptyState(): OnboardingState =
        OnboardingState(Loadable.Nothing(), OnboardingState.LocalState())
}
class WhenReceivingALoginTappedAction : TheOnboardingReducer() {

    private fun OnboardingState.withCredentials(email: Email = Email.Invalid, password: Password = Password.Invalid) : OnboardingState =
        copy(localState = OnboardingState.LocalState(email = email, password = password))

    @Test
    fun doesNothingIfTheEmailIsInvalid() {
        val initialState = emptyState().withCredentials(password = validPassword)
        var state = initialState
        val settableValue = state.toSettableValue { state = it }
        reducer.reduce(settableValue, OnboardingAction.LoginTapped, environment)
        state shouldEqual initialState
    }

    @Test
    fun doesNothingIfThePasswordIsInvalid() {
        val initialState = emptyState().withCredentials(email = validEmail)
        var state = initialState
        val settableValue = state.toSettableValue { state = it }
        reducer.reduce(settableValue, OnboardingAction.LoginTapped, environment)
        state shouldEqual initialState
    }

    @Test
    fun setsTheUserStateToLoadingIfBothStatesAreValid() {
        val initialState =
            emptyState().withCredentials(email = validEmail, password = validPassword)
        var state = initialState
        val settableValue = state.toSettableValue { state = it }
        reducer.reduce(settableValue, OnboardingAction.LoginTapped, environment)
        state.user shouldEqual Loadable.Loading<User>()
    }

    class WhenReceivingASetUserAction : TheOnboardingReducer() {

        @Test
        fun setsTheUserFromTheState() {
            val initialState = emptyState()
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            reducer.reduce(settableValue, OnboardingAction.SetUser(validUser), environment)
            state.user shouldEqual Loadable.Loaded(validUser)
        }
    }

    class WhenReceivingASetUserErrorAction : TheOnboardingReducer() {

        private val throwable = IllegalAccessException()

        @Test
        fun setsTheUserError() {
            val initialState = emptyState()
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            reducer.reduce(settableValue, OnboardingAction.SetUserError(throwable), environment)
            state.user shouldEqual Loadable.Error<User>(Failure(throwable, ""))
        }
    }

    class WhenReceivingAnEmailEnteredAction : TheOnboardingReducer() {

        @Test
        fun setsTheEmail() {
            val initialState = emptyState()
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            reducer.reduce(settableValue, OnboardingAction.EmailEntered(validEmail.toString()), environment)
            state.email shouldEqual validEmail
        }
    }

    class WhenReceivingAPasswordEnteredAction : TheOnboardingReducer() {

        @Test
        fun setsThePassword() {
            val initialState = emptyState()
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            reducer.reduce(settableValue, OnboardingAction.PasswordEntered(validPassword.toString()), environment)
            state.password shouldEqual validPassword
        }
    }
}