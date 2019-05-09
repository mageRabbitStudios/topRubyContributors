package com.kinzlstanislav.topcontributors.list.model.mapper

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.list.model.ContributorUI
import javax.inject.Inject

class ContributorListUIMapper @Inject constructor() {

    fun mapContributorsToContributorsUI(input: List<Contributor>): List<ContributorUI> {
        return emptyList()
    }

}