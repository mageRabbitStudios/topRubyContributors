package com.kinzlstanislav.topcontributors.unittesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseViewModelTest : BaseMockitoTest() {

    @get:Rule
    internal var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    internal var coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()
}