package com.kinzlstanislav.topcontributors.architecture.mapper

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.response.GithubRepositoryContributionAuthorResponse
import com.kinzlstanislav.topcontributors.architecture.response.GithubRepositoryContributorResponse
import javax.inject.Inject

class ContributorsMapper @Inject constructor() {

    fun mapFromContributorsResponse(response: List<GithubRepositoryContributorResponse>): List<Contributor> {
        val mappedResult: MutableList<Contributor> = mutableListOf()
        response.forEach { contributorResponse ->
            mappedResult.add(Contributor(
                authorName = contributorResponse.contributor?.name?: "",
                numberOfCommits = contributorResponse.numberOfCommits?: 0))
        }
        return mappedResult
    }
    
}