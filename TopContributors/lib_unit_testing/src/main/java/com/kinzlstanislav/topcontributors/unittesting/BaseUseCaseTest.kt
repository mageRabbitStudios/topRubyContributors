package com.kinzlstanislav.topcontributors.unittesting

import org.assertj.core.api.Assertions.assertThat

abstract class BaseUseCaseTest<RESULT: Any> : BaseMockitoTest() {

    protected lateinit var usecaseResult: RESULT

    protected fun thenResultIs(expectedResult: RESULT) {
        assertThat(usecaseResult).isEqualTo(expectedResult)
    }

}