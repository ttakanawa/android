package com.toggl.di

import android.content.Context
import androidx.room.Room
import com.toggl.TogglApplication
import com.toggl.data.db.AppDatabase
import com.toggl.data.db.TimeEntryDao
import com.toggl.timer.domain.TimeEntryLogReducer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

//    @Provides
//    @Singleton
//    fun appStore(environment: AppEnvironment): Store<AppState, AppAction> {
//
//        val combinedReducers = combine(
//            actionLoggingReducer,
//            appReducer,
//            globalTimerReducer,
//            globalOnboardingReducer
//        )
//
//        return Store.create(
//            initialState = AppState(),
//            reducer = combinedReducers,
//            environment = environment
//        )
//    }

//    @Provides
//    fun onboardingStore(store: Store<AppState, AppAction>): Store<OnboardingState, OnboardingAction> =
//        store.view(
//            mapToLocalState = {
//                OnboardingState(
//                    user = it.user,
//                    localState = it.onboardingLocalState
//                )
//            },
//            mapToGlobalAction = { AppAction.Onboarding(it) }
//        )
//
//    @Provides
//    fun timerStore(store: Store<AppState, AppAction>): Store<TimerState, TimerAction> =
//        store.view(
//            mapToLocalState = { TimerState(it.timeEntries, it.editedDescription) },
//            mapToGlobalAction = { AppAction.Timer(it) }
//        )

    @Provides
    fun provideContext(application: TogglApplication): Context = application.applicationContext
}