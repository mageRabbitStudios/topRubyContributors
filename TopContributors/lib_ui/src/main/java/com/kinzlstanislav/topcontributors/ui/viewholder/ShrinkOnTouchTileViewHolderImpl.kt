package com.kinzlstanislav.topcontributors.ui.viewholder

import android.view.View
import com.kinzlstanislav.topcontributors.architecture.core.extension.cancelOngoingAnimation
import com.kinzlstanislav.topcontributors.architecture.core.extension.setOnClickListener

class ShrinkOnTouchTileViewHolderImpl(private val itemView: View) : ShrinkOnTouchTileViewHolder {

    companion object {
        const val SHRINK_TO: Float = 0.95f
        const val FULL_SCALE: Float = 1.0f
        const val SHRINK_DURATION: Long = 300
    }

    /**
     * Very important to use this to assign touch actions to the ViewHolder that delegates this class.
     * Since we override onClickListener, the classic addOnClick or addOnTouch listeners in VH wouldn't work.
     * */
    override lateinit var touchUpAction: () -> Unit

    init {
        itemView.setOnClickListener(
                touchDown = { shrink() },
                touchUp = {
                    shrinkBack()
                    if (::touchUpAction.isInitialized) {
                        touchUpAction()
                    }
                },
                cancel = { shrinkBack() }
        )
    }

    override fun shrink() {
        itemView.cancelOngoingAnimation()
        itemView.animate()
                .scaleX(SHRINK_TO)
                .scaleY(SHRINK_TO)
                .duration = SHRINK_DURATION
    }

    override fun shrinkBack() {
        itemView.cancelOngoingAnimation()
        itemView.animate()
                .scaleX(FULL_SCALE)
                .scaleY(FULL_SCALE)
                .duration = SHRINK_DURATION
    }

}