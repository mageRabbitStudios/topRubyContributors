package com.kinzlstanislav.topcontributors.base.viewmodel

import androidx.lifecycle.ViewModel
import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope

abstract class BaseViewModel(appCoroutineScope: AppCoroutineScope) : ViewModel(), AppCoroutineScope by appCoroutineScope {

    override fun onCleared() {
        cancelAll()
        super.onCleared()
    }
}