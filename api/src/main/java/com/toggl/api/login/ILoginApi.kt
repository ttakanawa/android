package com.toggl.api.login

import com.toggl.models.domain.User
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password

interface ILoginApi {
    suspend fun login(email: Email.Valid, password: Password.Valid) : User
}