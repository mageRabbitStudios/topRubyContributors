package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsGenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsNetworkError

class ContributorsListViewModelImpl(
    appCoroutineScope: AppCoroutineScope,
    private val fetchRubyContributorsUseCase: FetchRubyContributorsUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val getLatLngFromUserAddressUseCase: GetLatLngFromAddressUseCase
) : ContributorsListViewModel(appCoroutineScope) {

    override val contributorsListState = MutableLiveData<ContributorsListViewModel.ContributorsListState>()

    override fun fetchRubyContributors() {
        contributorsListState.value = ContributorsListState.LoadingContributors
        ioJob {
            val result = fetchRubyContributorsUseCase.execute()

            when (result) {
                is FetchRubyContributorsUseCase.Result.Success -> contributorsListState.postValue(
                    ContributorsLoaded(result.contributors)
                )
                is FetchRubyContributorsUseCase.Result.NetworkError ->
                    contributorsListState.postValue(FetchingContributorsNetworkError)
                is FetchRubyContributorsUseCase.Result.GenericError ->
                    contributorsListState.postValue(FetchingContributorsGenericError)
            }
        }
    }

    override fun fetchContributorLocation(contributor: Contributor, observer: MediatorLiveData<ContributorsListViewModel.GetUserLocationResult>) {

        // first fetch the complete user data where "location" information is based on loginId provided with
        // the contributor response
        ioJob {
            val fetchUserResult = fetchUserUseCase.execute(contributor.loginName)
            when (fetchUserResult) {
                is FetchUserUseCase.Result.GenericError ->
                    observer.postValue(GetUserLocationResult.FetchingUserLocationGenericError)
                is FetchUserUseCase.Result.NetworkError ->
                    observer.postValue(GetUserLocationResult.FetchingUserLocationNetworkError)
                is FetchUserUseCase.Result.Success -> {

                    // then get latitude and longitude using Geocoder library
                    val getLatLngResult = getLatLngFromUserAddressUseCase.execute(fetchUserResult.user.address)
                    when (getLatLngResult) {
                        is GetLatLngFromAddressUseCase.Result.Error ->
                            observer.postValue(GetUserLocationResult.ParsingLocationError)
                        is GetLatLngFromAddressUseCase.Result.Success ->
                            observer.postValue(GetUserLocationResult.UserLocationLoaded(
                                getLatLngResult.location,
                                fetchUserResult.user))
                    }
                }
            }
        }
    }
}