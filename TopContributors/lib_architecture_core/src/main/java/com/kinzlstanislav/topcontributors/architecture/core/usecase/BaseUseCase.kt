package com.kinzlstanislav.topcontributors.architecture.core.usecase

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseUseCase {
    protected fun Exception.isConnectionError(): Boolean {
        return this is SocketTimeoutException ||
                this is ConnectException ||
                this is UnknownHostException
    }
}