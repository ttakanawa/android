package com.toggl.domain.reducers

import com.toggl.architecture.core.Reducer
import com.toggl.architecture.core.noEffect
import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.environment.services.analytics.AnalyticsService
import com.toggl.environment.services.analytics.Event

fun createAnalyticsReducer(analyticsService: AnalyticsService) =
    Reducer<AppState, AppAction> { _, action ->
        analyticsService.track(action.toEvent())
        noEffect()
    }

fun AppAction.toEvent(): Event? = null
