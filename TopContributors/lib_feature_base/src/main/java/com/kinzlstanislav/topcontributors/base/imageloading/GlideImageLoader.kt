package com.kinzlstanislav.topcontributors.base.imageloading

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.kinzlstanislav.topcontributors.base.R

class GlideImageLoader {

    private companion object {
        val SCALE_TYPE_WHILE_LOADING = ImageView.ScaleType.CENTER
    }

    fun loadFromUrl(context: Context, url: String, target: ImageView, showPlaceholder: Boolean = true) {
        Glide.with(context)
            .applyDefaultRequestOptions(RequestOptions()
                .centerCrop()
                .apply {
                    if (showPlaceholder) {
                        target.scaleType = SCALE_TYPE_WHILE_LOADING
                        placeholder((context.getDrawable(R.drawable.glide_placeholder) as AnimatedVectorDrawable).also {
                            it.start()
                        })
                    }
                })
            .load(url)
            .transition(withCrossFade())
            .into(target)
    }

}