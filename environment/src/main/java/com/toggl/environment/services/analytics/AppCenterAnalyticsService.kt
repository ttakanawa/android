package com.toggl.environment.services.analytics

import com.microsoft.appcenter.analytics.Analytics

class AppCenterAnalyticsService : AnalyticsService {
    override fun track(event: Event?) {
        event?.apply { Analytics.trackEvent(name, toMap()) }
    }
}
