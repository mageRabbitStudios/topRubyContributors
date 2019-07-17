package com.kinzlstanislav.topcontributors.feature.list.view

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.feature.list.view.adapter.ContributorsViewHolder
import com.kinzlstanislav.topcontributors.feature.list.view.sorter.ContributorsSorter
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsGenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsNetworkError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.LoadingContributors
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.list.R.id.generic_error
import com.kinzlstanislav.topcontributors.list.R.id.item_contributor_commits
import com.kinzlstanislav.topcontributors.list.R.id.item_contributor_name
import com.kinzlstanislav.topcontributors.list.R.id.network_error
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader
import com.kinzlstanislav.topcontributors.viewtesting.FragmentKoinTest
import com.kinzlstanislav.topcontributors.viewtesting.matchers.assertViewHolderOfItemAtPosition
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
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

private val RESULT_LOCATION_LOADED = GetUserLocationResult.UserLocationLoaded(
    location = SOME_LAT_LNG,
    user = User(
        name = SOME_CONTRIBUTORS.first().loginName,
        address = "New York"
    )
)

class FragmentContributorsListTest : FragmentKoinTest<FragmentContributorsList>() {

    @Mock
    lateinit var mockSorter: ContributorsSorter

    @Mock
    lateinit var mockViewModel: ContributorsListViewModel

    override fun setup() {
        super.setup()
        mockKoinForFragment {
            single { mockViewModel }
            single { mockSorter }
            single<GlideImageLoader> { mock(GlideImageLoader::class.java) }
        }

        given(mockSorter.sortFromTopByCommits(SOME_CONTRIBUTORS, 25))
            .willReturn(SOME_CONTRIBUTORS)

        given(mockViewModel.contributorsListState)
            .willReturn(subjectState)

        launchFragment()
    }

    private val subjectState = MutableLiveData<ContributorsListState>()

    override val fragmentInstance = FragmentContributorsList()

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

        givenGetUserLocationEventResultIs(RESULT_LOCATION_LOADED)
        clickListItem(list, 0)
        thenNavigateToMapFragment()
    }

    private fun whenStateIs(state: ContributorsListState) {
        subjectState.value = state
    }

    private fun givenGetUserLocationEventResultIs(result: GetUserLocationResult) {
        runBlocking {
            given(mockViewModel.fetchContributorLocation(any()))
                .willReturn(result)
        }
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
            subject.requireActivity().findViewById(list)!!,
            position,
            ContributorsViewHolder::class.java
        )
    }
}
