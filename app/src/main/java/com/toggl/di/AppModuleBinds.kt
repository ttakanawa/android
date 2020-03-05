package com.toggl.di

import android.app.Application
import com.toggl.TogglApplication
import dagger.Binds
import dagger.Module

@Module
abstract class AppModuleBinds {
    @Binds
    abstract fun provideApplication(bind: TogglApplication): Application
}