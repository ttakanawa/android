package com.toggl.di

import android.app.Application
import com.toggl.TogglApplication
import com.toggl.initializers.ThreeTenInitializer
import com.toggl.initializers.AppCenterInitializer
import com.toggl.initializers.AppInitializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
abstract class AppModuleBinds {
    @Binds
    abstract fun provideApplication(bind: TogglApplication): Application

    @Binds
    @IntoSet
    abstract fun provideAppCenterInitializer(bind: AppCenterInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideThreeTenInitializer(bind: ThreeTenInitializer): AppInitializer
}
