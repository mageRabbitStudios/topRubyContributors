package com.kinzlstanislav.topcontributors.feature.list.view.adapter

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor

interface ContributorItemClickListener {

    fun onContributorItemClicked(contributor: Contributor)

}