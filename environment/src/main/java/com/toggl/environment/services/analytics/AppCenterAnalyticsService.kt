package com.toggl.environment.services.analytics

import com.microsoft.appcenter.analytics.Analytics
import javax.inject.Inject

class AppCenterAnalyticsService @Inject constructor() : AnalyticsService {
    override fun track(event: Event?) {
        event?.apply { Analytics.trackEvent(name, toMap()) }
    }
}
