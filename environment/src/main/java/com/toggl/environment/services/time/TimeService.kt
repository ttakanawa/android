package com.toggl.environment.services.time

import org.threeten.bp.OffsetDateTime

interface TimeService {
    fun now(): OffsetDateTime
}
