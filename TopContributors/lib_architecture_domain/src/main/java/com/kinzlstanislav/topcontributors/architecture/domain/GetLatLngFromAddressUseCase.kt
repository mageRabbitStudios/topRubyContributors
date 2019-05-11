package com.kinzlstanislav.topcontributors.architecture.domain

import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetLatLngFromAddressUseCase @Inject constructor(private val geocoder: Geocoder) {

    fun execute(address: String): Result = try {
        val foundAddresses = geocoder.getFromLocationName(address, 1)
        val priorityAddress = foundAddresses.first()
        Result.Success(LatLng(priorityAddress.latitude, priorityAddress.longitude))
    } catch (exception: Exception) {
        Result.Error
    }

    sealed class Result {
        data class Success(val location: LatLng) : Result()
        object Error : Result()
    }

}