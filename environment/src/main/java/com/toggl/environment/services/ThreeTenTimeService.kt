package com.toggl.environment.services

import org.threeten.bp.OffsetDateTime

class ThreeTenTimeService : TimeService {
    override fun now(): OffsetDateTime =
        OffsetDateTime.now()
}
