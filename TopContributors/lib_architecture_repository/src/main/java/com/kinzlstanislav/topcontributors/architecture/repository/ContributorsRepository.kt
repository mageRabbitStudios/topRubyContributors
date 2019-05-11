package com.kinzlstanislav.topcontributors.architecture.repository

import com.kinzlstanislav.topcontributors.architecture.network.api.GithubApiService
import com.kinzlstanislav.topcontributors.architecture.network.mapper.ContributorsResponseMapper
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import java.io.IOException
import javax.inject.Inject

class ContributorsRepository @Inject constructor(
    private val api: GithubApiService,
    private val mapper: ContributorsResponseMapper
) {

    @Throws(IOException::class)
    suspend fun getRubyContributors(): List<Contributor> {
        val response = api.getContributorsForRubyAsync().await()
        return mapper.mapFromContributorsResponse(response)
    }

}