package com.toggl.environment.services.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.microsoft.appcenter.analytics.Analytics
import javax.inject.Inject

class AnalyticsServiceImpl @Inject constructor(context: Context) : AnalyticsService {
    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun track(event: Event?) {
        event?.run {
            val keyValues = toMap()
            firebaseAnalytics.logEvent(name, keyValues.toBundle())
            Analytics.trackEvent(name, keyValues)
        }
    }
}

private fun Map<String, String>.toBundle(): Bundle = Bundle().let { bundle ->
    this.keys.forEach {
        bundle.putString(it, this[it])
    }
    bundle
}
