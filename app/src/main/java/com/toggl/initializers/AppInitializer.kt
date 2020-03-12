package com.toggl.initializers

import com.toggl.TogglApplication

interface AppInitializer {
    fun initialize(togglApplication: TogglApplication)
}
