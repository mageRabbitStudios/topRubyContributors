package com.kinzlstanislav.topcontributors.list.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase

class ContributorsListViewModelImpl(
    appCoroutineScope: AppCoroutineScope,
    private val fetchRubyContributorsUseCase: FetchRubyContributorsUseCase
) : ContributorsListViewModel(appCoroutineScope) {

    override val state = MutableLiveData<ContributorsListViewModel.ContributorsListState>()

    override val fetchedContributors: List<Contributor> = emptyList()

    override fun fetchRubyContributors() = ioJob {
        state.postValue(ContributorsListState.Loading)

        val result = fetchRubyContributorsUseCase.execute()

        when (result) {
            is FetchRubyContributorsUseCase.Result.Success -> state.postValue(
                ContributorsListState.ContributorsFetched(
                    result.contributors
                )
            )
            is FetchRubyContributorsUseCase.Result.NetworkError -> state.postValue(ContributorsListState.NetworkError)
            is FetchRubyContributorsUseCase.Result.GenericError -> state.postValue(ContributorsListState.GenericError)
        }
    }

    override fun getMeTopContributors(top: Int) {

    }


}