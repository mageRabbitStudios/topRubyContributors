package com.kinzlstanislav.topcontributors.base.extensions

import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kinzlstanislav.topcontributors.base.view.BaseFragment

fun BaseFragment.disableTouchGestures() {
    requireActivity().window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun BaseFragment.enableTouchGestures() {
    requireActivity().window.clearFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun BaseFragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, length).show()
}

inline fun <reified T : Any> BaseFragment.bindArgument(key: String): Lazy<T> = lazy {
    arguments?.get(key) as? T ?: throw IllegalArgumentException("Argument not passed for key: $key")
}