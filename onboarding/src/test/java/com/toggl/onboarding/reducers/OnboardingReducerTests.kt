package com.toggl.onboarding.reducers

import com.toggl.api.login.LoginApi
import com.toggl.architecture.Loadable
import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.SettableValue
import com.toggl.models.domain.User
import com.toggl.models.validation.ApiToken
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.reducers.createOnboardingReducer
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.onboarding.domain.states.email
import com.toggl.onboarding.domain.states.password
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Test


abstract class BaseReducerTest<State, Action, Environment>(val reducer: Reducer<State, Action>) {
    abstract fun emptyState() : State

    fun State.toSettableValue(setFunction: (State) -> Unit) =
        SettableValue({ this }, setFunction)
}

fun <T> Loadable<T>.shouldBeLoading() =
    this shouldBeInstanceOf Loadable.Loading::class.java

infix fun <T> Loadable<T>?.shouldBeErrorWithThrowable(throwable: Throwable) {
    this shouldBeInstanceOf Loadable.Error::class.java
    val error = this as Loadable.Error<T>
    error.failure.throwable shouldBe throwable
}

infix fun <T> Loadable<T>?.shouldBeLoadedWith(value: T) {
    this shouldBeInstanceOf Loadable.Loaded::class.java
    val loaded = this as Loadable.Loaded<T>
    loaded.value shouldBe value
}

val loginApi = mock(LoginApi::class)

abstract class TheOnboardingReducer
    : BaseReducerTest<OnboardingState, OnboardingAction, LoginApi>(createOnboardingReducer(loginApi)) {

    companion object {
        val validPassword = Password.from("avalidpassword")
        val validEmail = Email.from("validemail@toggl.com")
        val validUser = User(
            ApiToken.from("12345678901234567890123456789012")
        )
    }

    override fun emptyState(): OnboardingState =
        OnboardingState(Loadable.Uninitialized, OnboardingState.LocalState())
}
class WhenReceivingALoginTappedAction : TheOnboardingReducer() {

    private fun OnboardingState.withCredentials(email: Email = Email.from(""), password: Password = Password.from("")) : OnboardingState =
        copy(localState = OnboardingState.LocalState(email = email, password = password))

    @Test
    fun doesNothingIfTheEmailIsInvalid() {
        val initialState = emptyState().withCredentials(password = validPassword)
        var state = initialState
        val settableValue = state.toSettableValue { state = it }
        reducer.reduce(settableValue, OnboardingAction.LoginTapped)
        state shouldEqual initialState
    }

    @Test
    fun doesNothingIfThePasswordIsInvalid() {
        val initialState = emptyState().withCredentials(email = validEmail)
        var state = initialState
        val settableValue = state.toSettableValue { state = it }
        reducer.reduce(settableValue, OnboardingAction.LoginTapped)
        state shouldEqual initialState
    }

    @Test
    fun setsTheUserStateToLoadingIfBothStatesAreValid() {
        val initialState =
            emptyState().withCredentials(email = validEmail, password = validPassword)
        var state = initialState
        val settableValue = state.toSettableValue { state = it }
        reducer.reduce(settableValue, OnboardingAction.LoginTapped)
        state.user.shouldBeLoading()
    }

    class WhenReceivingASetUserAction : TheOnboardingReducer() {

        @Test
        fun setsTheUserFromTheState() {
            val initialState = emptyState()
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            reducer.reduce(settableValue, OnboardingAction.SetUser(validUser))
            state.user shouldBeLoadedWith validUser
        }
    }

    class WhenReceivingASetUserErrorAction : TheOnboardingReducer() {

        private val throwable = IllegalAccessException()

        @Test
        fun setsTheUserError() {
            val initialState = emptyState()
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            reducer.reduce(settableValue, OnboardingAction.SetUserError(throwable))
            state.user shouldBeErrorWithThrowable throwable
        }
    }

    class WhenReceivingAnEmailEnteredAction : TheOnboardingReducer() {

        @Test
        fun setsTheEmail() {
            val initialState = emptyState()
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            reducer.reduce(settableValue, OnboardingAction.EmailEntered(validEmail.toString()))
            state.email.email shouldEqual validEmail.email
        }
    }

    class WhenReceivingAPasswordEnteredAction : TheOnboardingReducer() {

        @Test
        fun setsThePassword() {
            val initialState = emptyState()
            var state = initialState
            val settableValue = state.toSettableValue { state = it }
            reducer.reduce(settableValue, OnboardingAction.PasswordEntered(validPassword.toString()))
            state.password.password shouldEqual validPassword.password
        }
    }
}