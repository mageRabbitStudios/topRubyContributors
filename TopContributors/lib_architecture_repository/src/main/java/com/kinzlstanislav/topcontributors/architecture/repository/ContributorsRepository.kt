package com.kinzlstanislav.topcontributors.architecture.repository

import com.kinzlstanislav.topcontributors.architecture.repository.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.network.api.GithubApiService
import com.kinzlstanislav.topcontributors.architecture.repository.mapper.ContributorsResponseMapper
import java.io.IOException

class ContributorsRepository constructor(
    private val api: GithubApiService,
    private val mapper: ContributorsResponseMapper
) {

    @Throws(IOException::class)
    suspend fun fetchRubyContributors(): List<Contributor> {
        val response = api.getContributorsForRubyAsync().await()
        return mapper.mapFromContributorsResponse(response)
    }
}