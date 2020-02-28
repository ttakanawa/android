package com.toggl.environment

import com.toggl.api.login.LoginApi
import com.toggl.repository.Repository


class AppEnvironment(
    val loginApi: LoginApi,
    val repository: Repository
)