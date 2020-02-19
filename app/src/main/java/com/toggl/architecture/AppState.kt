package com.toggl.architecture

import com.toggl.architecture.Loadable.Nothing
import com.toggl.models.domain.User
import com.toggl.models.validation.Email
import com.toggl.models.validation.Password

data class AppState(
    val user: Loadable<User>,
    val email: Email,
    val password: Password
) {
    constructor() : this(
        user = Nothing(),
        email = Email.Invalid,
        password = Password.Invalid
    )
}