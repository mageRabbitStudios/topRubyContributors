package com.kinzlstanislav.topcontributors.architecture.network.mapper

import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.network.response.GithubUserResponse
import javax.inject.Inject

class UserResponseMapper @Inject constructor() {

    fun mapFromUserResponse(response: GithubUserResponse) = User(
        name = response.loginName ?: "",
        address = response.location ?: ""
    )

}