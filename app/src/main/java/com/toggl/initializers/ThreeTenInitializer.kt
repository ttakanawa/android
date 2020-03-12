package com.toggl.initializers

import com.jakewharton.threetenabp.AndroidThreeTen
import com.toggl.TogglApplication
import javax.inject.Inject

class ThreeTenInitializer @Inject constructor() : AppInitializer {
    override fun initialize(togglApplication: TogglApplication) =
        AndroidThreeTen.init(togglApplication)
}
