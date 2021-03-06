package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.base.extensions.coroutine
import com.kinzlstanislav.topcontributors.base.extensions.isConnectionError
import com.kinzlstanislav.topcontributors.architecture.repository.ContributorsRepository
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository
import com.kinzlstanislav.topcontributors.architecture.repository.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.repository.model.User
import com.kinzlstanislav.topcontributors.base.extensions.set
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.GenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.LoadingContributors
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.NetworkError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.OnUserLocationReceivedCallback.ContributorLocationResult.Error
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.OnUserLocationReceivedCallback.ContributorLocationResult.Received

class ContributorsListViewModel(
    private val contributorsRepository: ContributorsRepository,
    private val userRepository: UserRepository,
    private val geocoder: Geocoder,
    val state: MutableLiveData<ContributorsListState> = MutableLiveData()
) : ViewModel() {

    companion object {
        private const val MAX_SORTED_USERS = 20
    }

    interface OnUserLocationReceivedCallback {

        fun onUserLocationResultReceived(result: ContributorLocationResult)

        sealed class ContributorLocationResult {
            data class Received(val location: LatLng, val user: User) : ContributorLocationResult()
            object Error : ContributorLocationResult()
        }
    }

    sealed class ContributorsListState {

        // Fetching & sorting contributors
        data class ContributorsLoaded(val contributors: List<Contributor>) : ContributorsListState()

        object LoadingContributors : ContributorsListState()
        object NetworkError : ContributorsListState()
        object GenericError : ContributorsListState()
    }

    fun getRubyContributors() {
        state.set(LoadingContributors)
        coroutine {
            try {
                val contributors = contributorsRepository.fetchRubyContributors()
                val sortedContributors = contributors.sortedBy { -it.numberOfCommits }
                    .subList(0, MAX_SORTED_USERS)
                state.set(ContributorsLoaded(sortedContributors))
            } catch (exception: Exception) {
                state.set(if (exception.isConnectionError()) NetworkError else GenericError)
            }
        }
    }

    fun getUserLocation(
        contributor: Contributor,
        callback: OnUserLocationReceivedCallback
    ) {
        coroutine {
            try {
                val user = userRepository.fetchUserByLoginName(contributor.loginName)
                val foundAddresses = geocoder.getFromLocationName(user.address, 1)
                val location = LatLng(foundAddresses[0].latitude, foundAddresses[0].longitude)
                callback.onUserLocationResultReceived(Received(location, user))
            } catch (exception: Exception) {
                callback.onUserLocationResultReceived(Error)
            }
        }
    }
}