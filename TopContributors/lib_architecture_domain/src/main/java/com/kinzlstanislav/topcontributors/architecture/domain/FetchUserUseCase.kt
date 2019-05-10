package com.kinzlstanislav.topcontributors.architecture.domain

import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.usecase.BaseCoroutineUseCase
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    appCoroutineScope: AppCoroutineScope,
    private val userRepository: UserRepository
) : BaseCoroutineUseCase(appCoroutineScope) {

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