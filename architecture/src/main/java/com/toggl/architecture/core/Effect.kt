package com.toggl.architecture.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

typealias Effect<Action> = Flow<Action>

fun <T> noEffect(): Effect<T> = emptyFlow()
