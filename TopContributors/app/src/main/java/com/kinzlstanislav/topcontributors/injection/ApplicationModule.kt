package com.kinzlstanislav.topcontributors.injection

import android.app.Application
import android.content.Context
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AndroidDispatcherProvider
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScopeImpl
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.DispatcherProvider
import com.kinzlstanislav.topcontributors.architecture.core.dagger.qualifiers.ForApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideAppCoroutineScope(dispatcherProvider: DispatcherProvider): AppCoroutineScope = AppCoroutineScopeImpl(dispatcherProvider)

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = AndroidDispatcherProvider()


    @Provides
    @ForApplicationContext
    fun provideContext(application: Application): Context = application

}