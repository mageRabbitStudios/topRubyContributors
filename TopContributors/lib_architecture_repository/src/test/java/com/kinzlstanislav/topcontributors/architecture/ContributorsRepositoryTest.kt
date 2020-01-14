package com.kinzlstanislav.topcontributors.architecture

import com.kinzlstanislav.topcontributors.architecture.network.api.GithubApiService
import com.kinzlstanislav.topcontributors.architecture.network.response.GithubRepositoryContributorResponse
import com.kinzlstanislav.topcontributors.architecture.repository.ContributorsRepository
import com.kinzlstanislav.topcontributors.architecture.repository.mapper.ContributorsResponseMapper
import com.kinzlstanislav.topcontributors.architecture.repository.model.Contributor
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ContributorsRepositoryTest {

    private companion object {
        val SOME_LIST_OF_CONTRIBUTORS = listOf(Contributor("Login Name", "An Url", 20))
    }

    private val mockGithubApi = mockk<GithubApiService>()
    private val mockContributorsResponseMapper = mockk<ContributorsResponseMapper>()
    private val mockDeferredResponse = mockk<Deferred<List<GithubRepositoryContributorResponse>>>()
    private val mockResponse = mockk<List<GithubRepositoryContributorResponse>>()

    private val subject = ContributorsRepository(mockGithubApi, mockContributorsResponseMapper)

    @Test
    fun `fetchRubyContributors() - returns list of contributors`() {
        every { mockGithubApi.getContributorsForRubyAsync() } returns mockDeferredResponse
        coEvery { mockDeferredResponse.await() } returns mockResponse
        every {
            mockContributorsResponseMapper.mapFromContributorsResponse(mockResponse)
        } returns SOME_LIST_OF_CONTRIBUTORS

        val result = runBlocking { subject.fetchRubyContributors() }

        assertThat(result).isEqualTo(SOME_LIST_OF_CONTRIBUTORS)
    }
}