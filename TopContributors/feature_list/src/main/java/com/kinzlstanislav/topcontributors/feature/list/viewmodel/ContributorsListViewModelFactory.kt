package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase

class ContributorsListViewModelFactory(
    private val appCoroutineScope: AppCoroutineScope,
    private val fetchRubyContributorsUseCase: FetchRubyContributorsUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContributorsListViewModelImpl(appCoroutineScope, fetchRubyContributorsUseCase) as T
    }

}