package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import androidx.lifecycle.LiveData
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.base.viewmodel.BaseViewModel

abstract class ContributorsListViewModel(appCoroutineScope: AppCoroutineScope) : BaseViewModel(appCoroutineScope) {

    abstract val state: LiveData<ContributorsListState>

    sealed class ContributorsListState {
        data class ContributorsFetched(val contributors: List<Contributor>) : ContributorsListState()
        data class ContributorsSorted(val contributors: List<Contributor>) : ContributorsListState()
        object Loading : ContributorsListState()
        object NetworkError : ContributorsListState()
        object GenericError : ContributorsListState()
    }

    abstract fun fetchRubyContributors()

    abstract fun sortByTopByCommits(input: List<Contributor>, top: Int)

}