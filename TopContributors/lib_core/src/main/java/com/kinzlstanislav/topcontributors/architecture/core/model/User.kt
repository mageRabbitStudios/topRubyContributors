package com.kinzlstanislav.topcontributors.architecture.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Parcelize so I can pass it as argument into map fragment
@Parcelize
data class User(
    //TODO: User has only name & address here for the purposes of the demo app functionality showcase
    val name: String,
    val address: String
) : Parcelable