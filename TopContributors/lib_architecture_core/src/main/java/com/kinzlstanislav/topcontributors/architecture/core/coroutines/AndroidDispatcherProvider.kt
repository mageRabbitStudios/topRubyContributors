package com.kinzlstanislav.topcontributors.architecture.core.coroutines

import kotlinx.coroutines.Dispatchers

class AndroidDispatcherProvider : DispatcherProvider {

    override val background = Dispatchers.Default

    override val io = Dispatchers.IO

    override val ui = Dispatchers.Main
}