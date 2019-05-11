package com.kinzlstanislav.topcontributors.architecture.domain

import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase.Result.NetworkError
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase.Result.Success
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository
import com.kinzlstanislav.topcontributors.unittesting.BaseUseCaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val SOME_LOGIN = "stanislav"
private val SOME_USER = User("", "")

class FetchUserUseCaseTest : BaseUseCaseTest<FetchUserUseCase.Result>() {

    @Mock lateinit var mockUserRepository: UserRepository

    lateinit var subject: FetchUserUseCase

    @Before fun before() {
        subject = FetchUserUseCase(testAppCoroutineScope, mockUserRepository)
    }

    @Test
    fun `execute() - repository returns user, result should be SUCCESS`() {
        givenRepositoryReturns(SOME_USER)
        whenExecute()
        thenResultIs(Success(SOME_USER))
    }

    @Test
    fun `execute() - NETWORK_ERROR for SocketTimeoutException`() {
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

    private fun givenRepositoryReturns(response: User) = runBlocking {
        given(mockUserRepository.getUserByLoginName(SOME_LOGIN)).willReturn(response)
    }

    private fun givenRepositoryThrows(exception: Exception) = runBlocking {
        given(mockUserRepository.getUserByLoginName(SOME_LOGIN)).willThrow(exception)
    }

    private fun whenExecute() = runBlocking { usecaseResult = subject.execute(SOME_LOGIN) }

}