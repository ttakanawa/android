package com.toggl.database

import androidx.room.TypeConverter
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

class TogglTypeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): OffsetDateTime? =
        value?.let { Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC) }

    @TypeConverter
    fun dateToTimestamp(date: OffsetDateTime?): Long? {
        return date?.run { toInstant().toEpochMilli() }
    }

    @TypeConverter
    fun fromEpoch(value: Long?): Duration? =
        value?.let { Duration.ofMillis(it) }

    @TypeConverter
    fun toEpoch(duration: Duration?): Long? {
        return duration?.run { toMillis() }
    }
}
