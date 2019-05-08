package com.kinzlstanislav.topcontributors.base.coroutines

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AndroidDispatcherProvider @Inject constructor() : DispatcherProvider {

    override val background = Dispatchers.Default

    override val io = Dispatchers.IO

    override val ui = Dispatchers.Main
}