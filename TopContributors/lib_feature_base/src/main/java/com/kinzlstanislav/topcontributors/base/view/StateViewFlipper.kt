package com.kinzlstanislav.topcontributors.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ViewFlipper

class StateViewFlipper(context: Context, attrs: AttributeSet?
) : ViewFlipper(context, attrs) {

    init {
        // Animation Preferences
        setInAnimation(context, android.R.anim.fade_in)
        setOutAnimation(context, android.R.anim.fade_out)
    }

    fun showView(view: View) {
        if (indexOfChild(view) == -1) {
            addView(view)
        }
        if (currentView != view) {
            // Flip to specific view
            displayedChild = indexOfChild(view)
        }
    }
}