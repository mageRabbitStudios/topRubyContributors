package com.kinzlstanislav.topcontributors.architecture.core.usecase

import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseCoroutineUseCase(appCoroutineScope: AppCoroutineScope): BaseUseCase(),
    AppCoroutineScope by appCoroutineScope