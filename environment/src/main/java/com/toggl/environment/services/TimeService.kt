package com.toggl.environment.services

import org.threeten.bp.OffsetDateTime

interface TimeService {
    fun now(): OffsetDateTime
}
