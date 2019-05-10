package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.base.viewmodel.BaseViewModel

abstract class ContributorsListViewModel(appCoroutineScope: AppCoroutineScope) : BaseViewModel(appCoroutineScope) {

    abstract val contributorsListState: LiveData<ContributorsListState>

    abstract val getUserLocationResultState: LiveData<GetUserLocationResult>

    sealed class ContributorsListState {

        // Fetching & sorting contributors
        data class ContributorsFetched(val contributors: List<Contributor>) : ContributorsListState()
        data class ContributorsSorted(val contributors: List<Contributor>) : ContributorsListState()
        object LoadingContributors : ContributorsListState()
        object FetchingContributorsNetworkError : ContributorsListState()
        object FetchingContributorsGenericError : ContributorsListState()
    }

    sealed class GetUserLocationResult {

        // Fetching & processing contributor's location
        data class UserLocationFetched(val location: LatLng, val user: User) : GetUserLocationResult()
        object FetchingUserLocationNetworkError: GetUserLocationResult()
        object FetchingUserLocationGenericError : GetUserLocationResult()
        object ParsingLocationError : GetUserLocationResult()
    }

    abstract fun fetchRubyContributors()

    abstract fun sortByCommitsFromTop(input: List<Contributor>, howMany: Int)

    abstract fun fetchContributorLocation(contributor: Contributor)

}