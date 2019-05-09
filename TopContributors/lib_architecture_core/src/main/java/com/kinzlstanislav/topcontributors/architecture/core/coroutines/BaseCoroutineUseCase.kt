package com.kinzlstanislav.topcontributors.architecture.core.coroutines

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseCoroutineUseCase(appCoroutineScope: AppCoroutineScope) : AppCoroutineScope by appCoroutineScope {

    protected fun Exception.isConnectionError(): Boolean {
        return this is SocketTimeoutException ||
                this is ConnectException ||
                this is UnknownHostException
    }

}