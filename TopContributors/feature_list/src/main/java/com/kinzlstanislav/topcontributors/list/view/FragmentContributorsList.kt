package com.kinzlstanislav.topcontributors.list.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kinzlstanislav.topcontributors.base.view.BaseFragment
import com.kinzlstanislav.topcontributors.list.R
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.list.viewmodel.ContributorsListViewModel.ContributorsListState.*

class FragmentContributorsList : BaseFragment() {

    override val layoutResourceId = R.layout.fragment_contributors_list

    private lateinit var contributorsListViewModel: ContributorsListViewModel

    override fun onFragmentCreated() {
    }

    override fun observeState() {

        // Get the ContributorsListViewModel from the activity,
        // I'm fetching data from MainActivity just to showcase how to fetch data before entering the fragment,
        // so the content displays faster. Some Fragments can Inject their ViewModels and fetch data from inside.
        // its better for user experience to fetch the data on launch in most of the cases.
        contributorsListViewModel = requireActivity().run {
            ViewModelProviders.of(this).get(ContributorsListViewModel::class.java) }

        // It's better practise that the Observer has viewLifecycleOwner rather than "this" as a Fragment,
        // since if the fragment remains alive in the background, the LiveData would get emitted, which isn't desired.
        contributorsListViewModel.state.observe(viewLifecycleOwner, Observer { handleState(it) })

        // In cases like this it is important to call the handleState on launch to reflect the state on UI immediately,
        // (if we do not delay the coroutine of fetchRubyContributors function in VM, the Loading wouldn't be caught in
        // the fragment)
        contributorsListViewModel.state.value?.let { handleState(it) }
    }

    private fun handleState(state: ContributorsListViewModel.ContributorsListState) {
        when(state) {
            is Loading -> println("BAF: Loading")
            is NetworkError -> println("BAF: NetworkError")
            is GenericError -> println("BAF: GenericError")
            is ContributorsFetched -> println("BAF: ${state.contributors}")
        }
    }

}