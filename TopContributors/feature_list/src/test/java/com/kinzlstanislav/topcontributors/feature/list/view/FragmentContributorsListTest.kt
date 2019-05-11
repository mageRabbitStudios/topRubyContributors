package com.kinzlstanislav.topcontributors.feature.list.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.kinzlstanislav.topcontributors.feature.list.view.sorter.ContributorsSorter
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader
import com.kinzlstanislav.topcontributors.viewtesting.FragmentDaggerTest
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.mock

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

    @Test fun `blabla`() {
        launchFragment(subject, injector)
    }


}