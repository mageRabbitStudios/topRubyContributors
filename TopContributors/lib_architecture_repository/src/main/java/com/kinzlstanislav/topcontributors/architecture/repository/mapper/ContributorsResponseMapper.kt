package com.kinzlstanislav.topcontributors.architecture.repository.mapper

import com.kinzlstanislav.topcontributors.architecture.repository.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.network.response.GithubRepositoryContributorResponse

class ContributorsResponseMapper {

    fun mapFromContributorsResponse(response: List<GithubRepositoryContributorResponse>): List<Contributor> {
        val mappedResult: MutableList<Contributor> = mutableListOf()
        response.forEach { contributorResponse ->
            mappedResult.add(
                Contributor(
                    loginName = contributorResponse.contributor?.name ?: "",
                    numberOfCommits = contributorResponse.numberOfCommits ?: 0,
                    avatarUrl = contributorResponse.contributor?.avatarUrl ?: ""
                )
            )
        }
        return mappedResult
    }
}