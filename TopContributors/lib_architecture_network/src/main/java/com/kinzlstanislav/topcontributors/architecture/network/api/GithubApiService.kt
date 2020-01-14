package com.kinzlstanislav.topcontributors.architecture.network.api

import com.kinzlstanislav.topcontributors.architecture.network.response.GithubRepositoryContributorResponse
import com.kinzlstanislav.topcontributors.architecture.network.response.GithubUserResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    @GET("repos/ruby/ruby/stats/contributors")
    @Throws(Exception::class)
    fun getContributorsForRubyAsync(): Deferred<List<GithubRepositoryContributorResponse>>

    @GET("users/{userLoginName}")
    @Throws(Exception::class)
    fun getUserByNameAsync(@Path(value = "userLoginName") userLoginName: String): Deferred<GithubUserResponse>

}