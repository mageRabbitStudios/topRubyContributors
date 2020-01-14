package com.kinzlstanislav.topcontributors.architecture

import com.kinzlstanislav.topcontributors.architecture.network.api.GithubApiService
import com.kinzlstanislav.topcontributors.architecture.network.response.GithubUserResponse
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository
import com.kinzlstanislav.topcontributors.architecture.repository.mapper.UserResponseMapper
import com.kinzlstanislav.topcontributors.architecture.repository.model.User
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UserRepositoryTest {

    private companion object {
        const val SOME_NAME = "Jordan"
        val SOME_USER = User("Name", "Address")
    }

    private val mockGithubApi = mockk<GithubApiService>()
    private val mockUserResponseMapper = mockk<UserResponseMapper>()
    private val mockDeferredResponse = mockk<Deferred<GithubUserResponse>>()
    private val mockUserResponse = mockk<GithubUserResponse>()

    private val subject = UserRepository(mockGithubApi, mockUserResponseMapper)

    @Test
    fun `fetchUserByLoginName() - returns list of users`() {
        every { mockGithubApi.getUserByNameAsync(SOME_NAME) } returns mockDeferredResponse
        coEvery { mockDeferredResponse.await() } returns mockUserResponse
        every { mockUserResponseMapper.mapFromUserResponse(mockUserResponse) } returns SOME_USER

        val result = runBlocking { subject.fetchUserByLoginName(SOME_NAME) }

        assertThat(result).isEqualTo(SOME_USER)
    }
}