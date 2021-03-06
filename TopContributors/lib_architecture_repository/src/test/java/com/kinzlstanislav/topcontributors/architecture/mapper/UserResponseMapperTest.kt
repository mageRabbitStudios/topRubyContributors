package com.kinzlstanislav.topcontributors.architecture.mapper

import com.kinzlstanislav.topcontributors.architecture.repository.model.User
import com.kinzlstanislav.topcontributors.architecture.network.response.GithubUserResponse
import com.kinzlstanislav.topcontributors.architecture.repository.mapper.UserResponseMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UserResponseMapperTest {

    private val subject = UserResponseMapper()

    private companion object {
        val INPUT = GithubUserResponse(
            loginName = "stanislav",
            location = "New York"
        )
    }

    @Test
    fun `mapFromUserResponse()`() {
        // not null values
        assertThat(subject.mapFromUserResponse(INPUT))
            .isEqualTo(
                User(
                    INPUT.loginName!!,
                    INPUT.location!!
                )
            )
        // null values
        assertThat(subject.mapFromUserResponse(GithubUserResponse()))
            .isEqualTo(User("", ""))
    }

}