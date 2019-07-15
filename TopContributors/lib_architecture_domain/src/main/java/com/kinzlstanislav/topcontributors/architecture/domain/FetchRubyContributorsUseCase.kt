package com.kinzlstanislav.topcontributors.architecture.domain

import com.kinzlstanislav.topcontributors.architecture.core.coroutines.uiTask
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.usecase.BaseUseCase
import com.kinzlstanislav.topcontributors.architecture.repository.ContributorsRepository

class FetchRubyContributorsUseCase(
    private val contributorsRepository: ContributorsRepository
) : BaseUseCase() {

    @Suppress("TooGenericExceptionCaught")
    suspend fun execute(): Result = uiTask {
        try {
            val response = contributorsRepository.getRubyContributors()
            Result.Success(response)
        } catch (exception: Exception) {
            if (exception.isConnectionError()) Result.NetworkError else Result.GenericError
        }
    }

    sealed class Result {
        data class Success(val contributors: List<Contributor>) : Result()
        object NetworkError : Result()
        object GenericError : Result()
    }
}