package com.kinzlstanislav.topcontributors.feature.list.view

import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModelads.GetUserLocationResult.UserLocationLoaded
import com.kinzlstanislav.topcontributors.list.R.id.contributors_list_loader as loader
import com.kinzlstanislav.topcontributors.list.R.id.contributors_list_recycler_view as list

private val SOME_CONTRIBUTORS = listOf(
    Contributor(
        loginName = "Stanislav",
        avatarUrl = "url",
        numberOfCommits = 20
    ),
    Contributor(
        loginName = "Blazko",
        avatarUrl = "url",
        numberOfCommits = 9000
    )
)

private val SOME_LAT_LNG = LatLng(0.toDouble(), 0.toDouble())

private val RESULT_LOCATION_LOADED = UserLocationLoaded(
    location = SOME_LAT_LNG,
    user = User(
        name = SOME_CONTRIBUTORS.first().loginName,
        address = "New York"
    )
)

/*
class FragmentContributorsListTest : FragmentDaggerTest<FragmentContributorsList>() {

    @Mock
    lateinit var mockSorter: ContributorsSorter

    override var injector: (FragmentContributorsList) -> Unit = { fragment ->
        with(fragment) {
            imageLoader = mock(GlideImageLoader::class.java)
            contributorsSorter = mockSorter
            contributorsListViewModel = mock(ContributorsListViewModel::class.java)

            given(contributorsListViewModel.contributorsListState)
                .willReturn(subjectState)
            given(contributorsListViewModel.getUserLocationEvent)
                .willReturn(subjectGetUserLocationEvent)
            given(mockSorter.sortFromTopByCommits(SOME_CONTRIBUTORS, 25))
                .willReturn(SOME_CONTRIBUTORS)
        }
    }

    private val subjectState = MutableLiveData<ContributorsListState>()
    private val subjectGetUserLocationEvent = MutableLiveData<GetUserLocationResult>()

    override val subject = FragmentContributorsList()

    @Test
    fun fragmentFlow() {

        whenStateIs(LoadingContributors)
        loader.isVisible()

        whenStateIs(FetchingContributorsNetworkError)
        loader.isGone()
        network_error.isVisible()

        whenStateIs(FetchingContributorsGenericError)
        network_error.isGone()
        generic_error.isVisible()

        whenStateIs(ContributorsLoaded(SOME_CONTRIBUTORS))
        generic_error.isGone()
        list.isVisible()

        assertContributorItemDisplayed(0, "Stanislav", 20)
        assertContributorItemDisplayed(1, "Blazko", 9000)

        clickListItem(list, 0)
        whenGetUserLocationEventResultIs(RESULT_LOCATION_LOADED)
        thenNavigateToMapFragment()
    }

    private fun whenStateIs(state: ContributorsListState) {
        subjectState.value = state
    }

    private fun whenGetUserLocationEventResultIs(result: GetUserLocationResult) {
        subjectGetUserLocationEvent.value = result
    }

    private fun thenNavigateToMapFragment() {
        Mockito.verify(mockNavController)
            .navigate(eq(R.id.action_fragmentContributorsList_to_fragmentContributorMap), any())
    }

    private fun assertContributorItemDisplayed(position: Int, name: String, commits: Int) {
        assertDisplayedAtPosition(list, position, item_contributor_name, name)
        assertDisplayedAtPosition(
            list, position, item_contributor_commits,
            getResString(R.string.item_contributor_commits, commits.toString())
        )
        assertViewHolderOfItemAtPosition(
            getActivity()?.findViewById(list)!!,
            position,
            ContributorsViewHolder::class.java
        )
    }

}*/
