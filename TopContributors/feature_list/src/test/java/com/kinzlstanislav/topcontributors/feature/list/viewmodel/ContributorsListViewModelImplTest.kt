package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.ContributorsLoaded
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsGenericError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.FetchingContributorsNetworkError
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.ContributorsListState.LoadingContributors
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel.GetUserLocationResult
import com.kinzlstanislav.topcontributors.unittesting.BaseViewModelTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock

private val SOME_CONTRIBUTOR = Contributor("Contributor", "", 5)
private val SOME_USER = User("User", "Miami")
private val SOME_LATLNG = LatLng(10.toDouble(), 10.toDouble())

class ContributorsListViewModelImplTest : BaseViewModelTest<ContributorsListState>() {

    @Mock lateinit var mockFetchRubyContributorsUseCase: FetchRubyContributorsUseCase
    @Mock lateinit var mockFetchUserUseCase: FetchUserUseCase
    @Mock lateinit var mockGetLatLngFromAddressUseCase: GetLatLngFromAddressUseCase

    private lateinit var subject: ContributorsListViewModelImpl
    private val getUserLocationEventState: MutableLiveData<ContributorsListViewModel.GetUserLocationResult> = MutableLiveData()

    @Before
    fun before() {
        subject = ContributorsListViewModelImpl(
            testAppCoroutineScope,
            testState,
            getUserLocationEventState,
            mockFetchRubyContributorsUseCase,
            mockFetchUserUseCase,
            mockGetLatLngFromAddressUseCase) }

    // fetchRubyContributors()

    @Test fun `fetchRubyContributors() - LoadingContributors set`() {
        whenFetchRubyContributorsCalled()
        thenStateShouldBe(LoadingContributors)
    }

    @Test fun `fetchRubyContributors() - ContributorsLoaded set`() {
        givenFetchRubyContributorsUseCaseReturns(FetchRubyContributorsUseCase.Result.Success(emptyList()))
        whenFetchRubyContributorsCalled()
        thenStateShouldBe(ContributorsLoaded(emptyList()))
    }

    @Test fun `fetchRubyContributors() - FetchingContributorsNetworkError set`() {
        givenFetchRubyContributorsUseCaseReturns(FetchRubyContributorsUseCase.Result.NetworkError)
        whenFetchRubyContributorsCalled()
        thenStateShouldBe(FetchingContributorsNetworkError)
    }

    @Test fun `fetchRubyContributors() - FetchingContributorsGenericError set`() {
        givenFetchRubyContributorsUseCaseReturns(FetchRubyContributorsUseCase.Result.GenericError)
        whenFetchRubyContributorsCalled()
        thenStateShouldBe(FetchingContributorsGenericError)
    }

    // fetchContributorLocation()

    @Test fun `fetchContributorLocation() - should post FetchingUserLocationGenericError on observer`() {
        givenFetchUserUseCaseReturns(FetchUserUseCase.Result.GenericError)
        whenFetchContributorLocationCalled()
        thenGetUserLocationResultIs(GetUserLocationResult.FetchingUserLocationGenericError)
    }

    @Test fun `fetchContributorLocation() - should post FetchingUserLocationNetworkError on observer`() {
        givenFetchUserUseCaseReturns(FetchUserUseCase.Result.NetworkError)
        whenFetchContributorLocationCalled()
        thenGetUserLocationResultIs(GetUserLocationResult.FetchingUserLocationNetworkError)
    }

    @Test fun `fetchContributorLocation() - should post ParsingLocationError on observer`() {
        givenFetchUserUseCaseReturns(FetchUserUseCase.Result.Success(SOME_USER))
        givenGetLatLngFromAddressUseCaseReturns(GetLatLngFromAddressUseCase.Result.Error)
        whenFetchContributorLocationCalled()
        thenGetUserLocationResultIs(GetUserLocationResult.ParsingLocationError)
    }

    @Test fun `fetchContributorLocation() - should post UserLocationLoaded on observer`() {
        givenFetchUserUseCaseReturns(FetchUserUseCase.Result.Success(SOME_USER))
        givenGetLatLngFromAddressUseCaseReturns(GetLatLngFromAddressUseCase.Result.Success(SOME_LATLNG))
        whenFetchContributorLocationCalled()
        thenGetUserLocationResultIs(GetUserLocationResult.UserLocationLoaded(SOME_LATLNG, SOME_USER))
    }

    private fun givenFetchRubyContributorsUseCaseReturns(result: FetchRubyContributorsUseCase.Result) {
        runBlocking { given(mockFetchRubyContributorsUseCase.execute()).willReturn(result) }
    }

    private fun givenFetchUserUseCaseReturns(result: FetchUserUseCase.Result) {
        runBlocking { given(mockFetchUserUseCase.execute(SOME_CONTRIBUTOR.loginName)).willReturn(result) }
    }

    private fun givenGetLatLngFromAddressUseCaseReturns(result: GetLatLngFromAddressUseCase.Result) {
        runBlocking { given(mockGetLatLngFromAddressUseCase.execute(SOME_USER.address)).willReturn(result) }
    }

    private fun whenFetchContributorLocationCalled() {
        subject.fetchContributorLocation(SOME_CONTRIBUTOR)
    }

    private fun whenFetchRubyContributorsCalled() {
        subject.fetchRubyContributors()
    }

    private fun thenGetUserLocationResultIs(result: GetUserLocationResult) {
        assertThat(getUserLocationEventState.value).isEqualTo(result)
    }

}