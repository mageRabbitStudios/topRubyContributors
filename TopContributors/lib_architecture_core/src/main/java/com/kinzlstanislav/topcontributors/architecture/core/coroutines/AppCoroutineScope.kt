package com.kinzlstanislav.topcontributors.architecture.core.coroutines

import kotlinx.coroutines.CoroutineScope

interface AppCoroutineScope : CoroutineScope {

    fun uiJob(block: suspend CoroutineScope.() -> Unit)

    fun backgroundJob(block: suspend CoroutineScope.() -> Unit)

    fun ioJob(block: suspend CoroutineScope.() -> Unit)

    suspend fun <T> uiTask(block: suspend CoroutineScope.() -> T): T

    suspend fun <T> backgroundTask(block: suspend CoroutineScope.() -> T): T

    suspend fun <T> ioTask(block: suspend CoroutineScope.() -> T): T

    fun cancelAll()
}