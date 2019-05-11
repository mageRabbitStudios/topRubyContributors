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
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.LoadingContributors
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult.UserLocationLoaded
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.list.R.id.item_contributor_commits
import com.kinzlstanislav.topcontributors.list.R.id.item_contributor_name
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader
import com.kinzlstanislav.topcontributors.viewtesting.FragmentDaggerTest
import com.kinzlstanislav.topcontributors.viewtesting.matchers.assertViewHolderOfItemAtPosition
import com.kinzlstanislav.topcontributors.viewtesting.matchers.isGone
import com.kinzlstanislav.topcontributors.viewtesting.matchers.isVisible
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
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
        loginName = "Recruiter",
        avatarUrl = "url",
        numberOfCommits = 9000
    )
)

private val SOME_LAT_LNG = LatLng(0.toDouble(), 0.toDouble())

private val RESULT_LOCATION_LOADED = UserLocationLoaded(
    location = SOME_LAT_LNG,
    user = User(
        name = SOME_CONTRIBUTORS.first().loginName,
        address = "New York")
)

class FragmentContributorsListTest : FragmentDaggerTest<FragmentContributorsList>() {

    @Mock lateinit var mockSorter: ContributorsSorter

    override var injector: (FragmentContributorsList) -> Unit = {
        it.imageLoader = mock(GlideImageLoader::class.java)
        it.contributorsSorter = mockSorter
        it.contributorsListViewModel = mock(ContributorsListViewModel::class.java)

        given(it.contributorsListViewModel.contributorsListState)
            .willReturn(subjectState)
        given(it.contributorsListViewModel.getUserLocationEvent)
            .willReturn(subjectGetUserLocationEvent)
    }

    private val subjectState = MutableLiveData<ContributorsListState>()
    private val subjectGetUserLocationEvent = MutableLiveData<GetUserLocationResult>()

    override val subject = FragmentContributorsList()

    override fun setup() {
        super.setup()
        launchFragment(subject, injector)
        given(mockSorter.sortFromTopByCommits(SOME_CONTRIBUTORS, 25))
            .willReturn(SOME_CONTRIBUTORS)
    }

    @Test
    fun fragmentFlow() {
        loader.isGone()
        whenStateIs(LoadingContributors)
        loader.isVisible()

        list.isGone()
        whenStateIs(ContributorsLoaded(SOME_CONTRIBUTORS))
        list.isVisible()

        assertContributorItemDisplayed(
            position = 0,
            name = "Stanislav",
            commits = 20
        )

        assertContributorItemDisplayed(
            position = 1,
            name = "Recruiter",
            commits = 9000
        )

        clickListItem(list, 0)
        whenGetUserLocationEventResultIs(RESULT_LOCATION_LOADED)
        thenNavigateToMapFragment()
    }

    // TODO: Could write more tests for more states when implemented (no time)

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

}