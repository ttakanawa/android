package com.toggl.api.login

import com.toggl.models.domain.User
import com.toggl.models.validation.ApiToken
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.models.validation.toApiToken
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class MockLoginApi : ILoginApi {
    override fun login(email: Email.Valid, password: Password.Valid): Single<User> =
        Single.timer(2, TimeUnit.SECONDS).flatMap {
            when (val token = "12345678901234567890123456789012".toApiToken()) {
                is ApiToken.Valid -> Single.just(User(token))
                ApiToken.Invalid -> Single.error(NotImplementedError())
            }
        }
}