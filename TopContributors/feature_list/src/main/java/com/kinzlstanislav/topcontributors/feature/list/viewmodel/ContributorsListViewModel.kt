package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.model.User

abstract class ContributorsListViewModel : ViewModel() {

    abstract val contributorsListState: LiveData<ContributorsListState>

    sealed class ContributorsListState {

        // Fetching & sorting contributors
        data class ContributorsLoaded(val contributors: List<Contributor>) : ContributorsListState()
        object LoadingContributors : ContributorsListState()
        object FetchingContributorsNetworkError : ContributorsListState()
        object FetchingContributorsGenericError : ContributorsListState()
    }

    abstract fun fetchRubyContributors()

    abstract fun fetchContributorLocation(
        contributor: Contributor,
        onUserLocationFetchedAction: (GetUserLocationResult) -> Unit)

    sealed class GetUserLocationResult {

        // Fetching & processing contributor's location
        data class UserLocationLoaded(val location: LatLng, val user: User) : GetUserLocationResult()
        object FetchingUserLocationNetworkError: GetUserLocationResult()
        object FetchingUserLocationGenericError : GetUserLocationResult()
        object ParsingLocationError : GetUserLocationResult()
    }
}