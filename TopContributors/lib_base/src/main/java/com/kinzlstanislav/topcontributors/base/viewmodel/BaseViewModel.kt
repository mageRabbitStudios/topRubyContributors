package com.kinzlstanislav.topcontributors.base.viewmodel

import androidx.lifecycle.ViewModel
import com.kinzlstanislav.topcontributors.base.coroutines.AppCoroutineScope

abstract class BaseViewModel(appCoroutineScope: AppCoroutineScope) : ViewModel(), AppCoroutineScope by appCoroutineScope {

    override fun onCleared() {
        cancelAll()
        super.onCleared()
    }
}