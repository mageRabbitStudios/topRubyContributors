package com.kinzlstanislav.topcontributors.list.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.list.model.ContributorUI
import com.kinzlstanislav.topcontributors.list.model.mapper.ContributorListUIMapper
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel.ContributorsListState.*

class ContributorsListViewModelImpl(
    appCoroutineScope: AppCoroutineScope,
    private val fetchRubyContributorsUseCase: FetchRubyContributorsUseCase,
    private val contributorListUIMapper: ContributorListUIMapper
) : ContributorsListViewModel(appCoroutineScope) {

    override val state = MutableLiveData<ContributorsListViewModel.ContributorsListState>()

    override val fetchedContributors: List<ContributorUI>? = emptyList()

    override fun fetchRubyContributors() = uiJob {
        state.value = ContributorsListState.Loading

        val result = fetchRubyContributorsUseCase.execute()

        when (result) {
            is FetchRubyContributorsUseCase.Result.Success ->
                state.value =
                    ContributorsFetched(contributorListUIMapper.mapContributorsToContributorsUI(result.contributors))
            is FetchRubyContributorsUseCase.Result.NetworkError -> state.value = NetworkError
            is FetchRubyContributorsUseCase.Result.GenericError -> state.value = GenericError
        }
    }

    override fun getMeTopContributors(top: Int) {

    }


}