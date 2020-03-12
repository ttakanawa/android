package com.toggl.initializers

import com.toggl.TogglApplication
import javax.inject.Inject

class AppInitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards AppInitializer>
) {
    fun initialize(application: TogglApplication) =
        initializers.forEach { it.initialize(application) }
}
