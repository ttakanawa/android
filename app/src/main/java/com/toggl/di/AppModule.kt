/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2020, Toggl
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * © 2020 GitHub, Inc.
 */
package com.toggl.di

import android.content.Context
import com.toggl.TogglApplication
import com.toggl.architecture.core.FlowStore
import com.toggl.architecture.core.Store
import com.toggl.domain.AppAction
import com.toggl.domain.AppState
import com.toggl.domain.mappings.mapAppStateToOnboardingState
import com.toggl.domain.mappings.mapAppStateToTimerState
import com.toggl.domain.mappings.mapOnboardingActionToAppAction
import com.toggl.domain.mappings.mapTimerActionToAppAction
import com.toggl.domain.reducers.AppReducer
import com.toggl.domain.reducers.TimeEntryListReducer
import com.toggl.domain.reducers.createAppReducer
import com.toggl.domain.reducers.createTimeEntryListReducer
import com.toggl.onboarding.domain.actions.OnboardingAction
import com.toggl.onboarding.domain.reducers.OnboardingReducer
import com.toggl.onboarding.domain.states.OnboardingState
import com.toggl.repository.timeentry.TimeEntryRepository
import com.toggl.timer.common.domain.TimerAction
import com.toggl.timer.common.domain.TimerReducer
import com.toggl.timer.common.domain.TimerState
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@Module(includes = [AppModuleBinds::class])
class AppModule {

    @Provides
    fun provideContext(application: TogglApplication): Context = application.applicationContext

    @Provides
    @Singleton
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Named("appReducer")
    fun appReducer(
        @Named("timeEntryListReducer") timeEntryListReducer: TimeEntryListReducer,
        onboardingReducer: OnboardingReducer,
        timerReducer: TimerReducer
    ) = createAppReducer(
        timeEntryListReducer,
        onboardingReducer,
        timerReducer
    )

    @Provides
    @Singleton
    @Named("timeEntryListReducer")
    fun timeEntryListReducer(timeEntryRepository: TimeEntryRepository) =
        createTimeEntryListReducer(timeEntryRepository)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Provides
    @Singleton
    fun appStore(@Named("appReducer") appReducer: AppReducer): Store<AppState, AppAction> {
        return FlowStore.create(
            initialState = AppState(),
            reducer = appReducer
        )
    }

    @Provides
    @ExperimentalCoroutinesApi
    fun onboardingStore(store: Store<AppState, AppAction>): Store<OnboardingState, OnboardingAction> =
        store.view(
            mapToLocalState = ::mapAppStateToOnboardingState,
            mapToGlobalAction = ::mapOnboardingActionToAppAction
        )

    @Provides
    @ExperimentalCoroutinesApi
    fun timerStore(store: Store<AppState, AppAction>): Store<TimerState, TimerAction> =
        store.view(
            mapToLocalState = ::mapAppStateToTimerState,
            mapToGlobalAction = ::mapTimerActionToAppAction
        )
}
