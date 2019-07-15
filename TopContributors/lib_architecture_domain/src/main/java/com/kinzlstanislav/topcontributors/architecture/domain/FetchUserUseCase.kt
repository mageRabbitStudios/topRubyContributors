package com.kinzlstanislav.topcontributors.architecture.domain

import com.kinzlstanislav.topcontributors.architecture.core.coroutines.ioTask
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.core.usecase.BaseUseCase
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository

class FetchUserUseCase constructor(
    private val userRepository: UserRepository
) : BaseUseCase() {

    @Suppress("TooGenericExceptionCaught")
    suspend fun execute(userLoginName: String): Result = ioTask {
        try {
            val response = userRepository.getUserByLoginName(userLoginName)
            Result.Success(response)
        } catch (exception: Exception) {
            if (exception.isConnectionError()) Result.NetworkError else Result.GenericError
        }
    }

    sealed class Result {
        data class Success(val user: User) : Result()
        object NetworkError : Result()
        object GenericError : Result()
    }
}