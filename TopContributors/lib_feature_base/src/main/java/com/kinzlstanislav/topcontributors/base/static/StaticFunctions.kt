package com.kinzlstanislav.topcontributors.base.static

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun inflateViewForHolder(
    parent: ViewGroup,
    @LayoutRes itemViewLayout: Int
): View = LayoutInflater.from(parent.context).inflate(itemViewLayout, parent, false)