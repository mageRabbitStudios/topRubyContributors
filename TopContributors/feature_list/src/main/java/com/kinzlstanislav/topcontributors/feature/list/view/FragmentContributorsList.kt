package com.kinzlstanislav.topcontributors.feature.list.view

import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.base.Constants
import com.kinzlstanislav.topcontributors.base.view.BaseFragment
import com.kinzlstanislav.topcontributors.feature.list.view.adapter.ContributorItemClickListener
import com.kinzlstanislav.topcontributors.feature.list.view.adapter.ContributorsAdapter
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsFetched
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsSorted
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsGenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsNetworkError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.LoadingContributors
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult.FetchingUserLocationGenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult.FetchingUserLocationNetworkError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult.ParsingLocationError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult.UserLocationFetched
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_contributors_list.*
import kotlinx.android.synthetic.main.view_location_loading.*
import javax.inject.Inject

class FragmentContributorsList : BaseFragment(), ContributorItemClickListener {

    private companion object {
        const val LOADING = 0
        const val LIST = 1
        const val GENERIC_ERROR = 2
        const val NETWORK_ERROR = 3

        const val DISPLAY_X_CONTRIBUTORS = 25
    }

    override val layoutResourceId = R.layout.fragment_contributors_list

    @Inject
    lateinit var imageLoader: GlideImageLoader

    private lateinit var contributorsListViewModel: ContributorsListViewModel

    private lateinit var contributorsAdapter: ContributorsAdapter

    override fun onFragmentCreated() {
        contributorsAdapter = ContributorsAdapter(imageLoader, this)
        contributors_list_recycler_view.adapter = contributorsAdapter
    }

    override fun observeState() {
        contributorsListViewModel = requireActivity().run {
            ViewModelProviders.of(this).get(ContributorsListViewModel::class.java)
        }.apply {
            contributorsListState.observe(viewLifecycleOwner, Observer { handleContributorsState(it) })
        }
    }

    private fun handleContributorsState(state: ContributorsListState) = when (state) {
        is LoadingContributors -> contributors_list_flipper.displayedChild = LOADING
        is FetchingContributorsNetworkError -> contributors_list_flipper.displayedChild = NETWORK_ERROR
        is FetchingContributorsGenericError -> contributors_list_flipper.displayedChild = GENERIC_ERROR
        is ContributorsFetched -> contributorsListViewModel.sortByCommitsFromTop(
            state.contributors,
            DISPLAY_X_CONTRIBUTORS
        )
        is ContributorsSorted -> {
            contributors_list_flipper.displayedChild = LIST
            contributorsAdapter.updateItems(state.contributors)
        }
    }

    override fun onContributorItemClicked(contributor: Contributor) {
        showLoadingLocationView()
        contributorsListViewModel.getUserLocationResultState.observe(viewLifecycleOwner, Observer {
            hideLoadingLocationView()
            when (it) {
                is UserLocationFetched -> findNavController().navigate(R.id.action_fragmentContributorsList_to_fragmentContributorMap,
                    bundleOf(Constants.EXTRAS_LOCATION to it.location, Constants.EXTRAS_USER to it.user))
                is FetchingUserLocationNetworkError -> showToast("FetchingUserLocationNetworkError")
                is FetchingUserLocationGenericError -> showToast("FetchingUserLocationGenericError")
                is ParsingLocationError -> showToast("ParsingLocationError")
            }
            contributorsListViewModel.getUserLocationResultState.removeObservers(viewLifecycleOwner)
        })
        contributorsListViewModel.fetchContributorLocation(contributor)
    }

    private fun showLoadingLocationView() {
        disableTouch()
        contributor_location_loading_view.animate().alpha(1f).duration = 400
    }

    private fun hideLoadingLocationView() {
        enableTouch()
        contributor_location_loading_view.animate().alpha(0f).duration = 400
    }
}