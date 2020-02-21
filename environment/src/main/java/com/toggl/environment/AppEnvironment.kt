package com.toggl.environment

import com.toggl.api.login.ILoginApi
import com.toggl.data.IDataSource


class AppEnvironment(
    val loginApi: ILoginApi,
    val dataSource: IDataSource
)