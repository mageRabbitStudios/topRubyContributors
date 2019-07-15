package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import com.google.android.gms.maps.model.LatLng
import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.core.model.User
import com.kinzlstanislav.topcontributors.architecture.domain.FetchRubyContributorsUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.FetchUserUseCase
import com.kinzlstanislav.topcontributors.architecture.domain.GetLatLngFromAddressUseCase
import com.kinzlstanislav.topcontributors.unittesting.BaseViewModelTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock

private val SOME_CONTRIBUTOR = Contributor("Contributor", "", 5)
private val SOME_USER = User("User", "Miami")
private val SOME_LATLNG = LatLng(10.toDouble(), 10.toDouble())

class ContributorsListViewModelImplTest : BaseViewModelTest<ContributorsListViewModel.ContributorsListState>() {

    @Mock lateinit var mockFetchRubyContributorsUseCase: FetchRubyContributorsUseCase
    @Mock lateinit var mockFetchUserUseCase: FetchUserUseCase
    @Mock lateinit var mockGetLatLngFromAddressUseCase: GetLatLngFromAddressUseCase
    @Mock lateinit var mockOnUserLocationFetchedAction: (ContributorsListViewModel.GetUserLocationResult) -> Unit

    private lateinit var subject: ContributorsListViewModel

    @Before
    fun before() {
        subject = ContributorsListViewModel(
            testState,
            mockFetchRubyContributorsUseCase,
            mockFetchUserUseCase,
            mockGetLatLngFromAddressUseCase) }

    // fetchRubyContributors()

    @Test fun `fetchRubyContributors() - LoadingContributors set`() {
        whenFetchRubyContributorsCalled()
        thenStateShouldBe(ContributorsListViewModel.ContributorsListState.LoadingContributors)
    }

    @Test fun `fetchRubyContributors() - ContributorsLoaded set`() {
        givenFetchRubyContributorsUseCaseReturns(FetchRubyContributorsUseCase.Result.Success(emptyList()))
        whenFetchRubyContributorsCalled()
        thenStateShouldBe(ContributorsListViewModel.ContributorsListState.ContributorsLoaded(emptyList()))
    }

    @Test fun `fetchRubyContributors() - FetchingContributorsNetworkError set`() {
        givenFetchRubyContributorsUseCaseReturns(FetchRubyContributorsUseCase.Result.NetworkError)
        whenFetchRubyContributorsCalled()
        thenStateShouldBe(ContributorsListViewModel.ContributorsListState.FetchingContributorsNetworkError)
    }

    @Test fun `fetchRubyContributors() - FetchingContributorsGenericError set`() {
        givenFetchRubyContributorsUseCaseReturns(FetchRubyContributorsUseCase.Result.GenericError)
        whenFetchRubyContributorsCalled()
        thenStateShouldBe(ContributorsListViewModel.ContributorsListState.FetchingContributorsGenericError)
    }

    // fetchContributorLocation()

    @Test fun `fetchContributorLocation() - should post FetchingUserLocationGenericError on observer`() {
        givenFetchUserUseCaseReturns(FetchUserUseCase.Result.GenericError)
        whenFetchContributorLocationCalled()
        thenGetUserLocationResultIs(ContributorsListViewModel.GetUserLocationResult.FetchingUserLocationGenericError)
    }

    @Test fun `fetchContributorLocation() - should post FetchingUserLocationNetworkError on observer`() {
        givenFetchUserUseCaseReturns(FetchUserUseCase.Result.NetworkError)
        whenFetchContributorLocationCalled()
        thenGetUserLocationResultIs(ContributorsListViewModel.GetUserLocationResult.FetchingUserLocationNetworkError)
    }

    @Test fun `fetchContributorLocation() - should post ParsingLocationError on observer`() {
        givenFetchUserUseCaseReturns(FetchUserUseCase.Result.Success(SOME_USER))
        givenGetLatLngFromAddressUseCaseReturns(GetLatLngFromAddressUseCase.Result.Error)
        whenFetchContributorLocationCalled()
        thenGetUserLocationResultIs(ContributorsListViewModel.GetUserLocationResult.ParsingLocationError)
    }

    @Test fun `fetchContributorLocation() - should post UserLocationLoaded on observer`() {
        givenFetchUserUseCaseReturns(FetchUserUseCase.Result.Success(SOME_USER))
        givenGetLatLngFromAddressUseCaseReturns(GetLatLngFromAddressUseCase.Result.Success(SOME_LATLNG))
        whenFetchContributorLocationCalled()
        thenGetUserLocationResultIs(ContributorsListViewModel.GetUserLocationResult.UserLocationLoaded(SOME_LATLNG, SOME_USER))
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
        subject.fetchContributorLocation(SOME_CONTRIBUTOR, mockOnUserLocationFetchedAction)
    }

    private fun whenFetchRubyContributorsCalled() {
        subject.fetchRubyContributors()
    }

    private fun thenGetUserLocationResultIs(result: ContributorsListViewModel.GetUserLocationResult) {
        then(mockOnUserLocationFetchedAction).should().invoke(result)
    }

}