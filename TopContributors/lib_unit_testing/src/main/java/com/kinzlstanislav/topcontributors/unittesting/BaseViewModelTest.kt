package com.kinzlstanislav.topcontributors.unittesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseViewModelTest<VM_STATE : Any> : BaseMockitoTest() {

    @get:Rule
    internal var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    internal var coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()

    protected val testState: MutableLiveData<VM_STATE> = MutableLiveData()

    protected fun thenStateShouldBe(state: VM_STATE) {
        assertThat(testState.value).isEqualTo(state)
    }
}