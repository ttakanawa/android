package com.toggl.environment.services.time

import org.threeten.bp.OffsetDateTime

class ThreeTenTimeService : TimeService {
    override fun now(): OffsetDateTime =
        OffsetDateTime.now()
}
