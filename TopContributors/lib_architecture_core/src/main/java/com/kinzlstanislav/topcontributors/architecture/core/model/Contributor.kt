package com.kinzlstanislav.topcontributors.architecture.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contributor(
    val authorName: String,
    val avatarUrl: String,
    val numberOfCommits: Int
) : Parcelable