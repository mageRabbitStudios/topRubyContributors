package com.kinzlstanislav.topcontributors.feature.map.view

import com.kinzlstanislav.topcontributors.architecture.core.extension.bindArgument
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.base.Constants.EXTRAS_CONTRIBUTOR
import com.kinzlstanislav.topcontributors.base.view.BaseFragment
import com.kinzlstanislav.topcontributors.feature.map.R

class FragmentContributorMap : BaseFragment() {

    override val layoutResourceId = R.layout.fragment_contributor_map

    private val contributor: Contributor by bindArgument(EXTRAS_CONTRIBUTOR)

    override fun onFragmentCreated() {
        showToast(contributor.authorName)
    }

}