package com.kinzlstanislav.topcontributors.injection

import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppScopeViewModelFactoriesModule {

    @Provides
    fun provideContributorsListViewModelFactory(
        appCoroutineScope: AppCoroutineScope,
        fetchRubyContributorsUseCase: FetchRubyContributorsUseCase,
        fetchUserUseCase: FetchUserUseCase,
        getLatLngFromAddressUseCase: GetLatLngFromAddressUseCase
    ): ContributorsListViewModelFactory =
        ContributorsListViewModelFactory(
            appCoroutineScope,
            fetchRubyContributorsUseCase,
            fetchUserUseCase,
            getLatLngFromAddressUseCase)

}