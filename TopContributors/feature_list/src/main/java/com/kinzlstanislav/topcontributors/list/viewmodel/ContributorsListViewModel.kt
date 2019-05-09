package com.kinzlstanislav.topcontributors.list.viewmodel

import androidx.lifecycle.LiveData
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.base.viewmodel.BaseViewModel
import com.kinzlstanislav.topcontributors.list.model.ContributorUI

abstract class ContributorsListViewModel(appCoroutineScope: AppCoroutineScope) : BaseViewModel(appCoroutineScope) {

    abstract val state: LiveData<ContributorsListState>

    abstract val fetchedContributors: List<ContributorUI>?

    sealed class ContributorsListState {
        data class ContributorsFetched(val contributors: List<ContributorUI>) : ContributorsListState()
        object Loading : ContributorsListState()
        object NetworkError : ContributorsListState()
        object GenericError : ContributorsListState()
    }

    abstract fun fetchRubyContributors()

    abstract fun getMeTopContributors(top: Int)

}