package com.kinzlstanislav.topcontributors.base.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val background: CoroutineDispatcher

    val io: CoroutineDispatcher

    val ui: CoroutineDispatcher
}