package com.kinzlstanislav.topcontributors.architecture.mapper

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.response.GithubRepositoryContributorResponse
import javax.inject.Inject

class ContributorsMapper @Inject constructor() {

    fun mapFromContributorsResponse(contributors: List<GithubRepositoryContributorResponse>): List<Contributor> {
        return listOf()
    }

}