package com.toggl.environment

import com.toggl.api.login.ILoginApi
import com.toggl.repository.Repository


class AppEnvironment(
    val loginApi: ILoginApi,
    val repository: Repository
)