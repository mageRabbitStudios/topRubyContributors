package com.kinzlstanislav.topcontributors.architecture.domain

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase.Result.GenericError
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase.Result.NetworkError
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase.Result.Success
import com.kinzlstanislav.topcontributors.architecture.repository.ContributorsRepository
import com.kinzlstanislav.topcontributors.unittesting.BaseUseCaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import java.io.IOException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class FetchRubyContributorsUseCaseTest : BaseUseCaseTest<FetchRubyContributorsUseCase.Result>() {

    @Mock lateinit var mockContributorsRepository: ContributorsRepository

    lateinit var subject: FetchRubyContributorsUseCase

    @Before fun before() {
        subject = FetchRubyContributorsUseCase(testAppCoroutineScope, mockContributorsRepository)
    }

    @Test fun `execute() - repository returns list, result should be SUCCESS`() {
        givenRepositoryReturns(emptyList())
        whenExecute()
        thenResultIs(Success(emptyList()))
    }

    //TODO: Could have done next 3 with Parameterized
    @Test fun `execute() - NETWORK_ERROR for SocketTimeoutException`() {
        givenRepositoryThrows(SocketTimeoutException())
        whenExecute()
        thenResultIs(NetworkError)
    }

    @Test fun `execute() - NETWORK_ERROR for ConnectException`() {
        givenRepositoryThrows(ConnectException())
        whenExecute()
        thenResultIs(NetworkError)
    }

    @Test fun `execute() - NETWORK_ERROR for UnknownHostException`() {
        givenRepositoryThrows(UnknownHostException())
        whenExecute()
        thenResultIs(NetworkError)
    }

    @Test fun `execute() - GENERIC_ERROR for IOException`() {
        givenRepositoryThrows(IOException())
        whenExecute()
        thenResultIs(GenericError)
    }

    private fun givenRepositoryReturns(response: List<Contributor>) = runBlocking {
        given(mockContributorsRepository.getRubyContributors()).willReturn(response)
    }

    private fun givenRepositoryThrows(exception: Exception) = runBlocking {
        given(mockContributorsRepository.getRubyContributors()).willThrow(exception)
    }

    private fun whenExecute() = runBlocking { usecaseResult = subject.execute() }

}