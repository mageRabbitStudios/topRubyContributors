package com.kinzlstanislav.topcontributors.list.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.base.view.BaseFragment
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.list.view.adapter.ContributorItemClickListener
import com.kinzlstanislav.topcontributors.list.view.adapter.ContributorsAdapter
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel.ContributorsListState
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsFetched
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsSorted
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel.ContributorsListState.GenericError
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel.ContributorsListState.Loading
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel.ContributorsListState.NetworkError
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_contributors_list.*
import javax.inject.Inject

class FragmentContributorsList : BaseFragment(), ContributorItemClickListener {

    private companion object {
        const val LOADING = 0
        const val LIST = 1
        const val GENERIC_EROR = 2
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

        // Get the ContributorsListViewModel from the activity,
        // I'm fetching data from MainActivity just to showcase how to fetch data before entering the fragment,
        // so the content displays faster. Some Fragments can Inject their ViewModels and fetch data from inside.
        // its better for user experience to fetch the data on launch in most of the cases.
        contributorsListViewModel = requireActivity().run {
            ViewModelProviders.of(this).get(ContributorsListViewModel::class.java)
        }

        // It's better practise that the Observer has viewLifecycleOwner rather than "this" as a Fragment,
        // since if the fragment remains alive in the background, the LiveData would get emitted, which isn't desired.
        contributorsListViewModel.state.observe(viewLifecycleOwner, Observer { handleState(it) })

        // In cases like this it is important to call the handleState on launch to reflect the state on UI immediately,
        // (if we do not delay the coroutine of fetchRubyContributors function in VM, the Loading wouldn't be caught in
        // the fragment)
        contributorsListViewModel.state.value?.let { handleState(it) }
    }

    private fun handleState(state: ContributorsListState) {
        when (state) {
            is Loading -> contributors_list_flipper.displayedChild = LOADING
            is NetworkError -> contributors_list_flipper.displayedChild = NETWORK_ERROR
            is GenericError -> contributors_list_flipper.displayedChild = GENERIC_EROR
            is ContributorsFetched -> contributorsListViewModel.sortByTopByCommits(state.contributors, DISPLAY_X_CONTRIBUTORS)
            is ContributorsSorted -> {
                contributors_list_flipper.displayedChild = LIST
                contributorsAdapter.updateItems(state.contributors)
            }
        }
    }

    override fun onContributorItemClicked(contributor: Contributor) {
        showToast(contributor.toString())
    }
}