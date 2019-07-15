package com.kinzlstanislav.topcontributors.architecture.domain

import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase.Result.NetworkError
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase.Result.Success
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository
import com.kinzlstanislav.topcontributors.unittesting.BaseUseCaseTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val SOME_LOGIN = "stanislav"
private val SOME_USER = User("", "")

class FetchUserUseCaseTest : BaseUseCaseTest<FetchUserUseCase.Result>() {

    private val repository = mockk<UserRepository>()

    private val subject: FetchUserUseCase = FetchUserUseCase(repository)

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

    private fun givenRepositoryReturns(response: User) =
        coEvery { repository.getUserByLoginName(SOME_LOGIN) } returns response


    private fun givenRepositoryThrows(exception: Exception) =
        coEvery { repository.getUserByLoginName(SOME_LOGIN) } throws exception

    private fun whenExecute() = runBlocking { usecaseResult = subject.execute(SOME_LOGIN) }

}