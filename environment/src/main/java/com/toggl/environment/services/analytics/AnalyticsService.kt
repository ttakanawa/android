package com.toggl.environment.services.analytics

interface AnalyticsService {
    fun track(event: Event?)
}

interface Event {
    val name: String
    fun toMap(): Map<String, String>
}
