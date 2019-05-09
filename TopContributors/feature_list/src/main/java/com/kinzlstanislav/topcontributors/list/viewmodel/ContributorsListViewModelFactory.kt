package com.kinzlstanislav.topcontributors.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.list.model.mapper.ContributorListUIMapper

class ContributorsListViewModelFactory(
    private val appCoroutineScope: AppCoroutineScope,
    private val fetchRubyContributorsUseCase: FetchRubyContributorsUseCase,
    private val contributorListUIMapper: ContributorListUIMapper
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContributorsListViewModelImpl(appCoroutineScope, fetchRubyContributorsUseCase, contributorListUIMapper) as T
    }

}