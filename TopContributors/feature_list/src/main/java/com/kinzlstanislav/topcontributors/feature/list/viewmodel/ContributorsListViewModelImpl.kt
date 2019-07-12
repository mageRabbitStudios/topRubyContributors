package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsGenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsNetworkError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult.FetchingUserLocationGenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult.FetchingUserLocationNetworkError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult.ParsingLocationError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult.UserLocationLoaded

class ContributorsListViewModelImpl(
    appCoroutineScope: AppCoroutineScope,

    override val contributorsListState: MutableLiveData<ContributorsListState> = MutableLiveData(),
    override val getUserLocationEvent: MutableLiveData<GetUserLocationResult> = MutableLiveData(),

    private val fetchRubyContributorsUseCase: FetchRubyContributorsUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val getLatLngFromAddressUseCase: GetLatLngFromAddressUseCase
) : ContributorsListViewModel(appCoroutineScope) {

    override fun fetchRubyContributors() {
        contributorsListState.value = ContributorsListState.LoadingContributors
        uiJob {
            when (val result = fetchRubyContributorsUseCase.execute()) {
                is FetchRubyContributorsUseCase.Result.Success -> contributorsListState.postValue(
                    ContributorsLoaded(result.contributors)
                )
                is FetchRubyContributorsUseCase.Result.NetworkError ->
                    contributorsListState.value = FetchingContributorsNetworkError
                is FetchRubyContributorsUseCase.Result.GenericError ->
                    contributorsListState.value = FetchingContributorsGenericError
            }
        }
    }

    override fun fetchContributorLocation(contributor: Contributor) {

        // first fetch the complete user data where "location" information is based on loginId provided with
        // the contributor response
        uiJob {
            when (val fetchUserResult = fetchUserUseCase.execute(contributor.loginName)) {
                is FetchUserUseCase.Result.GenericError -> getUserLocationEvent.value = FetchingUserLocationGenericError
                is FetchUserUseCase.Result.NetworkError -> getUserLocationEvent.value = FetchingUserLocationNetworkError
                is FetchUserUseCase.Result.Success -> {

                    // then get latitude and longitude using Geocoder library
                    when (val getLatLngResult = getLatLngFromAddressUseCase.execute(fetchUserResult.user.address)) {
                        is GetLatLngFromAddressUseCase.Result.Error -> getUserLocationEvent.value = ParsingLocationError
                        is GetLatLngFromAddressUseCase.Result.Success ->
                            getUserLocationEvent.value = UserLocationLoaded(
                                getLatLngResult.location,
                                fetchUserResult.user)
                    }
                }
            }
        }
    }
}