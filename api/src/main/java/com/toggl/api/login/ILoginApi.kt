package com.toggl.api.login

import com.toggl.models.domain.User
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface ILoginApi {
    fun login(email: Email.Valid, password: Password.Valid) : Single<User>
}