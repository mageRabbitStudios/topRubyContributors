package com.kinzlstanislav.topcontributors.unittesting

import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TestAppCoroutineScope(private val coroutineDispatcherProvider: DispatcherProvider) : AppCoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = coroutineDispatcherProvider.ui + job

    override fun uiJob(block: suspend CoroutineScope.() -> Unit) {
        startJob(coroutineDispatcherProvider.ui, block)
    }

    override fun backgroundJob(block: suspend CoroutineScope.() -> Unit) {
        startJob(coroutineDispatcherProvider.background, block)
    }

    override fun ioJob(block: suspend CoroutineScope.() -> Unit) {
        startJob(coroutineDispatcherProvider.io, block)
    }

    override suspend fun <T> uiTask(block: suspend CoroutineScope.() -> T): T {
        return startTask(coroutineDispatcherProvider.ui, block)
    }

    override suspend fun <T> backgroundTask(block: suspend CoroutineScope.() -> T): T {
        return startTask(coroutineDispatcherProvider.background, block)
    }

    override suspend fun <T> ioTask(block: suspend CoroutineScope.() -> T): T {
        return startTask(coroutineDispatcherProvider.io, block)
    }

    override fun cancelAll() = job.cancelChildren()

    private fun startJob(
        coroutineContext: CoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        launch(coroutineContext) { block() }
    }

    private suspend fun <T> startTask(
        coroutineContext: CoroutineContext,
        block: suspend CoroutineScope.() -> T
    ): T {
        return withContext(coroutineContext) { block() }
    }
}