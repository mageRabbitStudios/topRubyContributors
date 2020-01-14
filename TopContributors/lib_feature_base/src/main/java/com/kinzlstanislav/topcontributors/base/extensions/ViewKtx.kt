package com.kinzlstanislav.topcontributors.base.extensions

import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver

fun View.setOnClickListener(touchDown: () -> Unit = {}, touchUp: () -> Unit = {}, cancel: () -> Unit = {}) {
    setOnTouchListener { _, event ->
        if (event != null) {
            when {
                event.action == MotionEvent.ACTION_DOWN -> touchDown()
                event.action == MotionEvent.ACTION_CANCEL -> cancel()
                event.action == MotionEvent.ACTION_UP -> touchUp()
            }
        }
        true
    }
}

fun View.afterMeasured(f: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

fun View.cancelOngoingAnimation() {
    if (animation != null) {
        animation.cancel()
    }
}
