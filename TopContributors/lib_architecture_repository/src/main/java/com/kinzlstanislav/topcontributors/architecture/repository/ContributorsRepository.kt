package com.kinzlstanislav.topcontributors.architecture.repository

import com.kinzlstanislav.topcontributors.architecture.network.api.GithubApiService
import com.kinzlstanislav.topcontributors.architecture.repository.mapper.ContributorsResponseMapper
import com.kinzlstanislav.topcontributors.architecture.repository.model.Contributor
import java.io.IOException

class ContributorsRepository(
    private val api: GithubApiService,
    private val mapper: ContributorsResponseMapper
) {

    @Throws(IOException::class)
    suspend fun fetchRubyContributors(): List<Contributor> =
        mapper.mapFromContributorsResponse(api.getContributorsForRubyAsync().await())
}