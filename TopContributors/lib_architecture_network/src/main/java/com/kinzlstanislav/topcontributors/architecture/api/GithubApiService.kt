package com.kinzlstanislav.topcontributors.architecture.api

import com.kinzlstanislav.topcontributors.architecture.response.GithubRepositoryContributorResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface GithubApiService {

    @GET("repos/ruby/ruby/stats/contributors")
    @Throws(Exception::class)
    fun getContributorsForRubyAsync(): Deferred<List<GithubRepositoryContributorResponse>>

}