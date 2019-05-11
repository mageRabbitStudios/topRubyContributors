package com.kinzlstanislav.topcontributors.architecture.domain

import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase.Result.Error
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase.Result.Success
import com.kinzlstanislav.topcontributors.unittesting.BaseUseCaseTest
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import java.io.IOException
import java.util.*

private const val SOME_ADDRESS_INPUT = "New York"
class GetLatLngFromAddressUseCaseTest : BaseUseCaseTest<GetLatLngFromAddressUseCase.Result>() {

    @Mock lateinit var mockGeocoder: Geocoder

    lateinit var subject: GetLatLngFromAddressUseCase

    @Before fun before() {
        subject = GetLatLngFromAddressUseCase(mockGeocoder)
    }

    @Test fun `execute() - geocoder returns address, result should be SUCCESS`() {
        givenGeocoderReturnsAddress()
        whenUseCaseExecuted()
        thenResultIs(Success(LatLng(0.toDouble(), 0.toDouble())))
    }

    @Test fun `execute() - geocoder throws IOException, result should be Error`() {
        givenGeocoderThrowsIOException()
        whenUseCaseExecuted()
        thenResultIs(Error)
    }

    private fun givenGeocoderThrowsIOException() {
        given(mockGeocoder.getFromLocationName(SOME_ADDRESS_INPUT, 1)).willThrow(IOException())
    }

    private fun whenUseCaseExecuted() {
        usecaseResult = subject.execute(SOME_ADDRESS_INPUT)
    }

    private fun givenGeocoderReturnsAddress() {
        val returnedAddress = Address(Locale.UK)
        returnedAddress.latitude = 0.toDouble()
        returnedAddress.longitude = 0.toDouble()
        val returnedAddresses = mutableListOf(returnedAddress)
        given(mockGeocoder.getFromLocationName(SOME_ADDRESS_INPUT, 1)).willReturn(returnedAddresses)
    }
}