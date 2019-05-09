package com.kinzlstanislav.topcontributors.injection

import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AndroidDispatcherProvider
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScopeImpl
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideAppCoroutineScope(dispatcherProvider: DispatcherProvider): AppCoroutineScope = AppCoroutineScopeImpl(dispatcherProvider)

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = AndroidDispatcherProvider()

}