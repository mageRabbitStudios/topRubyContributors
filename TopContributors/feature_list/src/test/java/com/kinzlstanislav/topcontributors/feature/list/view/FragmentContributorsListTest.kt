package com.kinzlstanislav.topcontributors.feature.list.view

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.feature.list.view.adapter.ContributorsViewHolder
import com.kinzlstanislav.topcontributors.feature.list.view.sorter.ContributorsSorter
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.LoadingContributors
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
import com.schibsted.spain.barista.interaction.BaristaListInteractions
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import com.kinzlstanislav.topcontributors.list.R.id.contributors_list_loader as loaderId
import com.kinzlstanislav.topcontributors.list.R.id.contributors_list_recycler_view as listId

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

class FragmentContributorsListTest : FragmentDaggerTest<FragmentContributorsList>() {

    @Mock lateinit var mockSorter: ContributorsSorter

    override var injector: (FragmentContributorsList) -> Unit = {
        it.imageLoader = mock(GlideImageLoader::class.java)
        it.contributorsSorter = mockSorter
        it.contributorsListViewModel = mock(ContributorsListViewModel::class.java)

        given(it.contributorsListViewModel.contributorsListState)
            .willReturn(subjectState)
    }

    private val subjectState = MutableLiveData<ContributorsListViewModel.ContributorsListState>()

    override val subject = FragmentContributorsList()

    override fun setup() {
        super.setup()
        launchFragment(subject, injector)
    }

    @Test fun seeLoading_thenSeeList_clickOnItem_seeUserLocationLoading_goToMap() {

        // see loading
        onView(withId(loaderId)).isGone()
        whenStateIs(LoadingContributors)
        onView(withId(loaderId)).isVisible()

        // then see list
        onView(withId(listId)).isGone()
        given(mockSorter.sortFromTopByCommits(SOME_CONTRIBUTORS, 25))
            .willReturn(SOME_CONTRIBUTORS)
        whenStateIs(ContributorsLoaded(SOME_CONTRIBUTORS))
        onView(withId(listId)).isVisible()
        onView(withId(loaderId)).isGone()

        // assert items are displayed on screen
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

        // go to map
        clickListItem(listId, 0)

        /*Mockito.verify(mockNavController)
            .navigate(eq(R.id.action_fragmentContributorsList_to_fragmentContributorMap), any())*/
    }

    private fun whenStateIs(state: ContributorsListViewModel.ContributorsListState) {
        subjectState.value = state
    }

    private fun assertContributorItemDisplayed(position: Int, name: String, commits: Int) {
        assertDisplayedAtPosition(listId, position, item_contributor_name, name)
        assertDisplayedAtPosition(listId, position, item_contributor_commits,
            getResString(R.string.item_contributor_commits, commits.toString()))
        assertViewHolderOfItemAtPosition(
            getActivity()?.findViewById(listId)!!,
            position,
            ContributorsViewHolder::class.java)
    }

}