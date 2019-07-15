package com.kinzlstanislav.topcontributors.architecture.core.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun ViewModel.uiJob(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.Main) {
        block()
    }
}

fun ViewModel.ioJob(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}

fun ViewModel.backgroundJob(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.Default) {
        block()
    }
}

suspend fun <T> uiTask(block: suspend CoroutineScope.() -> T): T {
    return startTask(Dispatchers.Main, block)
}

suspend fun <T> ioTask(block: suspend CoroutineScope.() -> T): T {
    return startTask(Dispatchers.IO, block)
}

suspend fun <T> backgroundTask(block: suspend CoroutineScope.() -> T): T {
    return startTask(Dispatchers.Default, block)
}

private suspend fun <T> startTask(
    coroutineContext: CoroutineContext,
    block: suspend CoroutineScope.() -> T
): T {
    return withContext(coroutineContext) { block() }
}