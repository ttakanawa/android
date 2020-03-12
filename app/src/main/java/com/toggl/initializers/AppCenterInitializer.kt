package com.toggl.initializers

import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.toggl.BuildConfig
import com.toggl.TogglApplication
import javax.inject.Inject

class AppCenterInitializer @Inject constructor() : AppInitializer {
    override fun initialize(togglApplication: TogglApplication) {
        if (!BuildConfig.DEBUG) {
            AppCenter.start(togglApplication, "TheAppSecret", Analytics::class.java)
        }
    }
}
