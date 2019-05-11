package com.kinzlstanislav.topcontributors.unittesting

import org.junit.runner.RunWith
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class BaseMockitoTest {

    @Spy
    protected val testAppCoroutineScope = TestAppCoroutineScope(TestCoroutineDispatcherProvider())
}