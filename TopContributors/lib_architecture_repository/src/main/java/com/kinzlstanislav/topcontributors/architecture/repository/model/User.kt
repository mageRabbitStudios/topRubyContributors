package com.kinzlstanislav.topcontributors.architecture.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String,
    val address: String
) : Parcelable