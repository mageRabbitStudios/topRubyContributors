package com.kinzlstanislav.topcontributors.feature.list.view

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.repository.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.repository.model.User
import com.kinzlstanislav.topcontributors.feature.list.view.adapter.ContributorsViewHolder
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.OnUserLocationReceivedCallback.ContributorLocationResult
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.OnUserLocationReceivedCallback.ContributorLocationResult.Received
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.list.R.id.item_contributor_commits
import com.kinzlstanislav.topcontributors.list.R.id.item_contributor_name
import com.kinzlstanislav.topcontributors.base.imageloading.GlideImageLoader
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.viewtesting.FragmentKoinTest
import com.kinzlstanislav.topcontributors.viewtesting.matchers.assertViewHolderOfItemAtPosition
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
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

class FragmentContributorsListTest : FragmentKoinTest<FragmentContributorsList>() {

    private val mockViewModel = mockk<ContributorsListViewModel>(relaxed = true)

    override fun setup() {
        super.setup()
        mockKoinForFragment {
            single { mockViewModel }
            single<GlideImageLoader> { mock(GlideImageLoader::class.java) }
        }

        every { mockViewModel.state } returns subjectState

        launchFragment()
    }

    override val fragmentInstance = FragmentContributorsList()
    private val subjectState = MutableLiveData<ContributorsListState>()

    @Test
    fun fragmentFlow() {

        loader.assertItsVisible()

        whenStateChangesTo(ContributorsLoaded(SOME_CONTRIBUTORS))

        loader.assertItsGone()

        assertContributorItemDisplayed(0, "Stanislav", 20)
        assertContributorItemDisplayed(1, "Blazko", 9000)

        givenFetchUserLocationReturns(
            Received(
                LatLng(5.0, 5.0),
                User("", "")
            )
        ) // could test the error too potentially

        clickListItem(list, 0)
        thenNavigateToMapFragment()
    }


    private fun whenStateChangesTo(state: ContributorsListState) {
        subjectState.value = state
    }

    private fun givenFetchUserLocationReturns(result: ContributorLocationResult) {
        every { mockViewModel.getUserLocation(any(), any()) } answers {
            subject.onUserLocationResultReceived(result)
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
