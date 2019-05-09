package com.kinzlstanislav.topcontributors.list.view.adapter

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor

interface ContributorItemClickListener {

    fun onContributorItemClicked(contributor: Contributor)

}