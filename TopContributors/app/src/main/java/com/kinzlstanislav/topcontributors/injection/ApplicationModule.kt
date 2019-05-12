package com.kinzlstanislav.topcontributors.injection

import android.app.Application
import android.content.Context
import android.location.Geocoder
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AndroidDispatcherProvider
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScopeImpl
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.DispatcherProvider
import com.kinzlstanislav.topcontributors.architecture.core.dagger.qualifiers.ForApplicationContext
import com.kinzlstanislav.topcontributors.architecture.core.dagger.scopes.PerActivity
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase
import com.kinzlstanislav.topcontributors.architecture.network.injection.NetworkModule
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModelFactory
import dagger.Module
import dagger.Provides

@Module(includes = [
    AppScopeViewModelFactoriesModule::class,
    NetworkModule::class
])
class ApplicationModule {

    @Provides
    fun provideAppCoroutineScope(dispatcherProvider: DispatcherProvider): AppCoroutineScope = AppCoroutineScopeImpl(dispatcherProvider)

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = AndroidDispatcherProvider()

    @Provides
    fun provideGeocoder(@ForApplicationContext context: Context): Geocoder = Geocoder(context)

    @Provides
    @ForApplicationContext
    fun provideContext(application: Application): Context = application

}