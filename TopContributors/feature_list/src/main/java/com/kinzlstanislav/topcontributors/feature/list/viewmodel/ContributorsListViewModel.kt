package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.uiJob
import com.kinzlstanislav.topcontributors.architecture.core.extension.isConnectionError
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.repository.ContributorsRepository
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorLocationResult.Error
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorLocationResult.Received
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.*

class ContributorsListViewModel(

    internal val contributorsListState: MutableLiveData<ContributorsListState> = MutableLiveData(),

    private val contributorsRepository: ContributorsRepository,
    private val userRepository: UserRepository,
    private val geocoder: Geocoder
) : ViewModel() {

    /**  Results and states */

    sealed class ContributorsListState {

        // Fetching & sorting contributors
        data class ContributorsLoaded(val contributors: List<Contributor>) : ContributorsListState()

        object LoadingContributors : ContributorsListState()
        object NetworkError : ContributorsListState()
        object GenericError : ContributorsListState()
    }

    sealed class ContributorLocationResult {
        data class Received(val location: LatLng, val user: User) : ContributorLocationResult()
        object Error : ContributorLocationResult()
    }

    /**  functions */

    fun fetchRubyContributors() {
        contributorsListState.value = LoadingContributors
        uiJob {
            try {
                val contributors = contributorsRepository.getRubyContributors()
                contributorsListState.value =
                    ContributorsLoaded(contributors)
            } catch (exception: Exception) {
                if (exception.isConnectionError()) {
                    contributorsListState.value = NetworkError
                } else {
                    contributorsListState.value = GenericError
                }
            }
        }
    }

    fun fetchContributorLocation(
        contributor: Contributor,
        onFetched: (ContributorLocationResult) -> Unit
    ) {
        uiJob {
            try {
                val user = userRepository.getUserByLoginName(contributor.loginName)
                val foundAddresses = geocoder.getFromLocationName(user.address, 1)
                val location = LatLng(foundAddresses.first().latitude, foundAddresses.first().longitude)
                onFetched(Received(location, user))

            } catch (exception: Exception) {
                onFetched(Error)
            }
        }
    }
}