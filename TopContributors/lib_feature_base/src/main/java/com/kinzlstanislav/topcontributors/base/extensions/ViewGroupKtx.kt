package com.kinzlstanislav.topcontributors.base.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflateViewHolder(@LayoutRes itemViewLayout: Int): View =
    LayoutInflater.from(context).inflate(itemViewLayout, this, false)