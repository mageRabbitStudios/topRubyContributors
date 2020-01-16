package com.kinzlstanislav.topcontributors.base.extensions

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<*>.set(state: Any) {
    value = state
}