package com.kinzlstanislav.topcontributors.feature.list.injection

import androidx.lifecycle.ViewModelProviders
import com.kinzlstanislav.topcontributors.architecture.core.dagger.scopes.PerFragment
import com.kinzlstanislav.topcontributors.feature.list.view.FragmentContributorsList
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ContributorsListModule {

    // loads the view model from fragment's activity (main activity) layer
    @Provides
    @PerFragment
    fun provideContributorsListViewModel(
        fragment: FragmentContributorsList,
        factory: ContributorsListViewModelFactory
    ): ContributorsListViewModel = ViewModelProviders.of(fragment.requireActivity(), factory)
        .get(ContributorsListViewModel::class.java)

}