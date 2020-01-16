package com.kinzlstanislav.topcontributors.architecture.repository.mapper

import com.kinzlstanislav.topcontributors.architecture.repository.model.User
import com.kinzlstanislav.topcontributors.architecture.network.response.GithubUserResponse

class UserResponseMapper {

    fun mapFromUserResponse(response: GithubUserResponse) =
        User(
            name = response.loginName ?: "",
            address = response.location ?: ""
        )

}