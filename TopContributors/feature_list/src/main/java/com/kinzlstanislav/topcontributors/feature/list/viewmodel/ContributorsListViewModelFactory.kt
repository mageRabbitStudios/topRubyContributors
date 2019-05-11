package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase

class ContributorsListViewModelFactory(
    private val appCoroutineScope: AppCoroutineScope,
    private val fetchRubyContributorsUseCase: FetchRubyContributorsUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val getLatLngFromAddressUseCase: GetLatLngFromAddressUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContributorsListViewModelImpl(
            appCoroutineScope = appCoroutineScope,
            fetchRubyContributorsUseCase = fetchRubyContributorsUseCase,
            fetchUserUseCase = fetchUserUseCase,
            getLatLngFromAddressUseCase = getLatLngFromAddressUseCase) as T
    }

}