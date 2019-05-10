package com.kinzlstanislav.topcontributors.injection

import androidx.lifecycle.ViewModelProviders
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.architecture.core.dagger.scopes.PerActivity
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModelFactory
import com.kinzlstanislav.topcontributors.view.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    @PerActivity
    fun provideContributorsListViewModel(
        activity: MainActivity,
        factory: ContributorsListViewModelFactory
    ): ContributorsListViewModel = ViewModelProviders.of(activity, factory).get(ContributorsListViewModel::class.java)

    @Provides
    @PerActivity
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