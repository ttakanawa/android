package com.toggl.database

import com.toggl.database.dao.TimeEntryDao

interface TogglDatabase {
    fun timeEntryDao(): TimeEntryDao
}