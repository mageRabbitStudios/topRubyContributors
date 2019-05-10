package com.kinzlstanislav.topcontributors.architecture.repository

import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.network.api.GithubApiService
import com.kinzlstanislav.topcontributors.architecture.network.mapper.UserMapper
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: GithubApiService,
    private val mapper: UserMapper
) {

    @Throws(IOException::class)
    suspend fun getUserByLoginName(userLoginName: String): User {
        val response = api.getUserByNameAsync(userLoginName).await()
        return mapper.mapFromUserResponse(response)
    }

}