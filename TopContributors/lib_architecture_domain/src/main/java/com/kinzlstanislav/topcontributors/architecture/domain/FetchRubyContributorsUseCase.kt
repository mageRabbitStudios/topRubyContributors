package com.kinzlstanislav.topcontributors.architecture.domain

import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.usecase.BaseCoroutineUseCase
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.repository.ContributorsRepository
import java.lang.Exception
import javax.inject.Inject

class FetchRubyContributorsUseCase @Inject constructor(
    appCoroutineScope: AppCoroutineScope,
    private val contributorsRepository: ContributorsRepository
) : BaseCoroutineUseCase(appCoroutineScope) {

    @Suppress("TooGenericExceptionCaught")
    suspend fun execute(): Result = ioTask {
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