package com.toggl.environment.services.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsService @Inject constructor(context: Context) : AnalyticsService {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun track(event: Event?) {
        event?.apply { firebaseAnalytics.logEvent(name, toMap().toBundle()) }
    }

    private fun Map<String, String>.toBundle(): Bundle = Bundle().let { bundle ->
        this.keys.forEach {
            bundle.putString(it, this[it])
        }
        bundle
    }
}
