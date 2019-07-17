package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import android.location.Geocoder
import androidx.lifecycle.LiveData
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
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.GenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.LoadingContributors
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.NetworkError

class ContributorsListViewModel(
    private val contributorsRepository: ContributorsRepository,
    private val userRepository: UserRepository,
    private val geocoder: Geocoder
) : ViewModel() {

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

    private val _contributorsListState: MutableLiveData<ContributorsListState> = MutableLiveData()
    val contributorsListState: LiveData<ContributorsListState> = _contributorsListState

    fun fetchRubyContributors() {
        _contributorsListState.value = LoadingContributors
        uiJob {
            _contributorsListState.value = try {
                val contributors = contributorsRepository.getRubyContributors()
                ContributorsLoaded(contributors)
            } catch (exception: Exception) {
                if (exception.isConnectionError()) NetworkError else GenericError
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
                val location = LatLng(foundAddresses[0].latitude, foundAddresses[0].longitude)
                onFetched(Received(location, user))

            } catch (exception: Exception) {
                onFetched(Error)
            }
        }
    }
}