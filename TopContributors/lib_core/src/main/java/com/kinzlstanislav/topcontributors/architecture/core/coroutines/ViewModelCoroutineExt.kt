package com.kinzlstanislav.topcontributors.architecture.core.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.uiJob(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.Main) {

    }
}