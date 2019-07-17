package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.uiJob
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsGenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsNetworkError

class ContributorsListViewModel(

    internal val contributorsListState: MutableLiveData<ContributorsListState> = MutableLiveData(),

    private val fetchRubyContributorsUseCase: FetchRubyContributorsUseCase,
    private val fetchUserUseCase: FetchUserUseCase,
    private val getLatLngFromAddressUseCase: GetLatLngFromAddressUseCase
) : ViewModel() {

    /**  Results and states */

    sealed class ContributorsListState {

        // Fetching & sorting contributors
        data class ContributorsLoaded(val contributors: List<Contributor>) : ContributorsListState()

        object LoadingContributors : ContributorsListState()
        object FetchingContributorsNetworkError : ContributorsListState()
        object FetchingContributorsGenericError : ContributorsListState()
    }

    sealed class ContributorLocationResult {
        data class Received(val location: LatLng, val user: User) : ContributorLocationResult()
        object Error : ContributorLocationResult()
    }

    /**  functions */

    fun fetchRubyContributors() {
        contributorsListState.value = ContributorsListState.LoadingContributors
        uiJob {
            when (val result = fetchRubyContributorsUseCase.execute()) {
                is FetchRubyContributorsUseCase.Result.Success -> contributorsListState.value =
                    ContributorsListState.ContributorsLoaded(result.contributors)
                is FetchRubyContributorsUseCase.Result.NetworkError ->
                    contributorsListState.value = FetchingContributorsNetworkError
                is FetchRubyContributorsUseCase.Result.GenericError ->
                    contributorsListState.value = FetchingContributorsGenericError
            }
        }
    }

    fun fetchContributorLocation(
        contributor: Contributor,
        onFetched: (ContributorLocationResult) -> Unit
    ) {
        uiJob {
            // first fetch the complete user data where "location" information is based on loginId provided with
            // the contributor response
            when (val fetchUserResult = fetchUserUseCase.execute(contributor.loginName)) {
                is FetchUserUseCase.Result.GenericError -> { /*...*/
                }
                is FetchUserUseCase.Result.NetworkError -> { /*...*/
                }
                is FetchUserUseCase.Result.Success -> {

                    // then get latitude and longitude using Geocoder library
                    when (val getLatLngResult = getLatLngFromAddressUseCase.execute(fetchUserResult.user.address)) {
                        is GetLatLngFromAddressUseCase.Result.Error -> { /*...*/
                        }
                        is GetLatLngFromAddressUseCase.Result.Success -> onFetched(
                            ContributorLocationResult.Received(
                                getLatLngResult.location,
                                fetchUserResult.user
                            )
                        )
                    }
                }
            }
        }
    }
}