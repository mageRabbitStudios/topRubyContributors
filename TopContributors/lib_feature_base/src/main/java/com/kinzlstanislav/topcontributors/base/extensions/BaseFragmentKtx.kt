package com.kinzlstanislav.topcontributors.base.extensions

import com.kinzlstanislav.topcontributors.base.view.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun BaseFragment.fragmentJob(block: suspend CoroutineScope.() -> Unit): Job {
    return fragmentScope.launch(Dispatchers.Main) {
        block()
    }
}