package com.kinzlstanislav.topcontributors.feature.list.view

import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kinzlstanislav.topcontributors.architecture.core.extension.observe
import com.kinzlstanislav.topcontributors.base.Constants
import com.kinzlstanislav.topcontributors.base.view.BaseFragment
import com.kinzlstanislav.topcontributors.feature.list.view.adapter.ContributorsAdapter
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.GenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.LoadingContributors
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.NetworkError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.OnUserLocationReceivedCallback.ContributorLocationResult
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_contributors_list.*
import kotlinx.android.synthetic.main.view_location_loading.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import kotlinx.android.synthetic.main.fragment_contributors_list.contributors_list_flipper as flipper

class FragmentContributorsList : BaseFragment(),
    ContributorsListViewModel.OnUserLocationReceivedCallback {

    private companion object {
        const val GETTING_USER_LOCATION_VIEW_ANIM_APP_DUR = 400L
    }

    override val layoutResourceId = R.layout.fragment_contributors_list

    private val imageLoader: GlideImageLoader by inject()
    private val contributorsListViewModel: ContributorsListViewModel by sharedViewModel()

    private val contributorsAdapter: ContributorsAdapter by lazy {
        ContributorsAdapter(
            imageLoader,
            onItemClickAction = {
                showLoadingLocationView()
                contributorsListViewModel.getUserLocation(it, this@FragmentContributorsList)
            }
        )
    }

    override fun onFragmentCreated() {
        observe(contributorsListViewModel.state, viewStateObserver)
        contributors_list_recycler_view.adapter = contributorsAdapter
    }

    private val viewStateObserver: Observer<ContributorsListState> = Observer { state ->
        with(flipper) {
            when (state) {
                is LoadingContributors -> showView(contributors_list_loader)
                is NetworkError -> showView(network_error)
                is GenericError -> showView(generic_error)
                is ContributorsLoaded -> {
                    showView(contributors_list_recycler_view)
                    contributorsAdapter.updateItems(state.contributors)
                }
            }
        }
    }

    override fun onUserLocationResultReceived(result: ContributorLocationResult) {
        hideLoadingLocationView()
        when (result) {
            is ContributorLocationResult.Received -> findNavController().navigate(
                R.id.action_fragmentContributorsList_to_fragmentContributorMap,
                bundleOf(Constants.EXTRAS_LOCATION to result.location, Constants.EXTRAS_USER to result.user)
            )
        }
    }

    private fun showLoadingLocationView() {
        disableTouch()
        contributor_location_loading_view.animate().alpha(1f).duration = GETTING_USER_LOCATION_VIEW_ANIM_APP_DUR
    }

    private fun hideLoadingLocationView() {
        enableTouch()
        contributor_location_loading_view.animate().alpha(0f).duration = GETTING_USER_LOCATION_VIEW_ANIM_APP_DUR
    }
}