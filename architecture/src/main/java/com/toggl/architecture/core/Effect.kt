package com.toggl.architecture.core

import kotlinx.coroutines.flow.Flow

typealias Effect<Action> = Flow<Action>
