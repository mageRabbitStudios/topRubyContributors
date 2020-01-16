package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.repository.ContributorsRepository
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository
import com.kinzlstanislav.topcontributors.base.extensions.isConnectionError
import com.kinzlstanislav.topcontributors.base.extensions.set
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.GenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.LoadingContributors
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.NetworkError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.OnUserLocationReceivedCallback
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.OnUserLocationReceivedCallback.ContributorLocationResult.Error
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.OnUserLocationReceivedCallback.ContributorLocationResult.Received
import com.kinzlstanislav.topcontributors.unittesting.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Test
import java.net.ConnectException

class ContributorsListViewModelTest : BaseViewModelTest() {

    private val mockContributorsRepository = mockk<ContributorsRepository>()
    private val mockUserRepository = mockk<UserRepository>()
    private val mockGeocoder = mockk<Geocoder>()
    private val mockAddresses = mockk<List<Address>>()
    private val mockOnUserLocationReceivedCallback = mockk<OnUserLocationReceivedCallback>(relaxed = true)
    private val mockState = mockk<MutableLiveData<ContributorsListState>>(relaxed = true)

    private val subject = ContributorsListViewModel(
        mockContributorsRepository,
        mockUserRepository,
        mockGeocoder,
        mockState
    )

    @Before
    fun setup() {
        mockkStatic("com.kinzlstanislav.topcontributors.base.extensions.ExceptionKtxKt")
    }

    @Test
    fun `getRubyContributors() - ContributorsLoaded`() {
        coEvery { mockContributorsRepository.fetchRubyContributors() } returns TEST_CONTRIBUTORS_25
        subject.getRubyContributors()
        verifyOrder {
            with(mockState) {
                set(LoadingContributors)
                set(ContributorsLoaded(EXPECTED_TEST_CONTRIBUTORS_20))
            }
        }
    }

    @Test
    fun `getRubyContributors() - NetworkError`() {
        every { Exception().isConnectionError() } returns true
        coEvery { mockContributorsRepository.fetchRubyContributors() } throws ConnectException()
        subject.getRubyContributors()
        verifyOrder {
            with(mockState) {
                set(LoadingContributors)
                set(NetworkError)
            }
        }
    }

    @Test
    fun `getRubyContributors() - GenericError`() {
        every { Exception().isConnectionError() } returns false
        coEvery { mockContributorsRepository.fetchRubyContributors() } throws NullPointerException()
        subject.getRubyContributors()
        verifyOrder {
            with(mockState) {
                set(LoadingContributors)
                set(GenericError)
            }
        }
    }

    @Test
    fun `getUserLocation() - Received callback`() {
        coEvery { mockUserRepository.fetchUserByLoginName(SOME_CONTRIBUTOR_NAME) } returns SOME_USER
        coEvery { mockGeocoder.getFromLocationName(SOME_USER.address, any()) } returns mockAddresses
        every { mockAddresses[0].latitude } returns 0.1
        every { mockAddresses[0].longitude } returns 0.0

        subject.getUserLocation(SOME_CONTRIBUTOR, mockOnUserLocationReceivedCallback)

        verify {
            mockOnUserLocationReceivedCallback.onUserLocationResultReceived(
                Received(LatLng(0.1, 0.0), SOME_USER)
            )
        }
    }

    @Test
    fun `getUserLocation() - Error callback`() {
        coEvery { mockUserRepository.fetchUserByLoginName(SOME_CONTRIBUTOR_NAME) } throws NullPointerException()

        subject.getUserLocation(SOME_CONTRIBUTOR, mockOnUserLocationReceivedCallback)

        verify {
            mockOnUserLocationReceivedCallback.onUserLocationResultReceived(Error)
        }
    }
}

