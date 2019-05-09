package com.kinzlstanislav.topcontributors.architecture.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val background: CoroutineDispatcher

    val io: CoroutineDispatcher

    val ui: CoroutineDispatcher
}