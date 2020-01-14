package com.kinzlstanislav.topcontributors.base.viewholder

interface ShrinkOnTouchTileViewHolder {

    var touchUpAction: () -> Unit

    fun shrink()

    fun shrinkBack()
}
