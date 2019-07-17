package com.kinzlstanislav.topcontributors.feature.list.view

import android.location.Address
import android.location.Geocoder
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.repository.ContributorsRepository
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository
import com.kinzlstanislav.topcontributors.feature.list.view.adapter.ContributorsViewHolder
import com.kinzlstanislav.topcontributors.feature.list.view.sorter.ContributorsSorter
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.list.R.id.item_contributor_commits
import com.kinzlstanislav.topcontributors.list.R.id.item_contributor_name
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader
import com.kinzlstanislav.topcontributors.viewtesting.FragmentKoinTest
import com.kinzlstanislav.topcontributors.viewtesting.matchers.assertViewHolderOfItemAtPosition
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import io.mockk.coEvery
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

    private val mockSorter = mockk<ContributorsSorter>()
    private val mockContributorsRepository = mockk<ContributorsRepository>()
    private val mockUserRepository = mockk<UserRepository>()
    private val mockGeocoder = mockk<Geocoder>()
    private val mockUser = mockk<User>()
    private val mockAddresses = mockk<List<Address>>()

    private val viewModel = ContributorsListViewModel(
        mockContributorsRepository,
        mockUserRepository,
        mockGeocoder
    )

    override fun setup() {
        super.setup()
        mockKoinForFragment {
            single { viewModel }
            single { mockSorter }
            single<GlideImageLoader> { mock(GlideImageLoader::class.java) }
        }

        every { mockSorter.sortFromTopByCommits(SOME_CONTRIBUTORS, 25) } returns SOME_CONTRIBUTORS
        coEvery { mockContributorsRepository.getRubyContributors() } returns SOME_CONTRIBUTORS
        coEvery { mockUserRepository.getUserByLoginName(any()) } returns mockUser
        every { mockUser.address } returns "some string"
        every { mockGeocoder.getFromLocationName("some string", 1) } returns mockAddresses
        every { mockAddresses[0].latitude } returns 0.0
        every { mockAddresses[0].longitude } returns 0.0

        launchFragment()
    }

    override val fragmentInstance = FragmentContributorsList()

    @Test
    fun fragmentFlow() {

        loader.isVisible()

        viewModel.fetchRubyContributors()

        loader.isGone()

        assertContributorItemDisplayed(0, "Stanislav", 20)
        assertContributorItemDisplayed(1, "Blazko", 9000)

        clickListItem(list, 0)
        thenNavigateToMapFragment()
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
