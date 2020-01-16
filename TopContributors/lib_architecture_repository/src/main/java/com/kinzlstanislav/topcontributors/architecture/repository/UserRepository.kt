package com.kinzlstanislav.topcontributors.architecture.repository

import com.kinzlstanislav.topcontributors.architecture.network.api.GithubApiService
import com.kinzlstanislav.topcontributors.architecture.repository.mapper.UserResponseMapper
import java.io.IOException

class UserRepository(
    private val api: GithubApiService,
    private val mapper: UserResponseMapper
) {

    @Throws(IOException::class)
    suspend fun fetchUserByLoginName(userLoginName: String) =
        mapper.mapFromUserResponse(api.getUserByNameAsync(userLoginName).await())

}