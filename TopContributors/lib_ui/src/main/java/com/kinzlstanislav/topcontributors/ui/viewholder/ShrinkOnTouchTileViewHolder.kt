package com.kinzlstanislav.topcontributors.ui.viewholder

interface ShrinkOnTouchTileViewHolder {

    var touchUpAction: () -> Unit

    fun shrink()

    fun shrinkBack()
}
