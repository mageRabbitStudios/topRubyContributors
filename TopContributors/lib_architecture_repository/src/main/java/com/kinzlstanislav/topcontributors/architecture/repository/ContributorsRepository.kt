package com.kinzlstanislav.topcontributors.architecture.repository

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.network.api.GithubApiService
import com.kinzlstanislav.topcontributors.architecture.network.mapper.ContributorsResponseMapper
import java.io.IOException

class ContributorsRepository constructor(
    private val api: GithubApiService,
    private val mapper: ContributorsResponseMapper
) {

    @Throws(IOException::class)
    suspend fun getRubyContributors(): List<Contributor> {
        val response = api.getContributorsForRubyAsync().await()
        return mapper.mapFromContributorsResponse(response)
    }

}