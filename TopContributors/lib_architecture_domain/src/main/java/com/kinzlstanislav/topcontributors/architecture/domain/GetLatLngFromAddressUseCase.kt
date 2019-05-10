package com.kinzlstanislav.topcontributors.architecture.domain

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.dagger.qualifiers.ForApplicationContext
import com.kinzlstanislav.topcontributors.architecture.core.usecase.BaseUseCase
import javax.inject.Inject

class GetLatLngFromAddressUseCase @Inject constructor(@ForApplicationContext val context: Context) : BaseUseCase() {

    fun execute(address: String): Result = try {
        val coder = Geocoder(context)
        val foundAddress = coder.getFromLocationName(address, 1).first()
        Result.Success(LatLng(foundAddress.latitude, foundAddress.longitude))
    } catch (exception: Exception) {
        Result.Error
    }

    sealed class Result {
        data class Success(val location: LatLng) : Result()
        object Error : Result()
    }

}