package com.toggl.api.login

import com.toggl.models.domain.User
import com.toggl.models.validation.ApiToken
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import com.toggl.models.validation.toApiToken
import kotlinx.coroutines.delay

class MockLoginApi : LoginApi {
    override suspend fun login(email: Email.Valid, password: Password.Valid): User {
        delay(2000)

        return when (val token = "12345678901234567890123456789012".toApiToken()) {
            is ApiToken.Valid -> User(token)
            ApiToken.Invalid -> throw NotImplementedError()
        }
    }
}