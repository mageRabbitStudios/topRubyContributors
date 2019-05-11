package com.kinzlstanislav.topcontributors.unittesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseViewModelTest<VM_STATE : Any> : BaseMockitoTest() {

    @get:Rule
    internal var rule: TestRule = InstantTaskExecutorRule()

    protected val testState: MutableLiveData<VM_STATE> = MutableLiveData()

    protected fun thenStateShouldBe(state: VM_STATE) {
        assertThat(testState.value).isEqualTo(state)
    }

}