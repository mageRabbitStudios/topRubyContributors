package com.kinzlstanislav.topcontributors.architecture.core.extension

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Exception.isConnectionError(): Boolean {
    return this is SocketTimeoutException ||
            this is ConnectException ||
            this is UnknownHostException
}