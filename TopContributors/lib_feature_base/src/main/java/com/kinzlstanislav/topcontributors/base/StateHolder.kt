package com.kinzlstanislav.topcontributors.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class StateHoldingViewModel<T> : ViewModel() {
    private val state = MutableLiveData<T>()
    fun getState() = state
    protected fun setState(with: T) {
        state.value = with
    }
}