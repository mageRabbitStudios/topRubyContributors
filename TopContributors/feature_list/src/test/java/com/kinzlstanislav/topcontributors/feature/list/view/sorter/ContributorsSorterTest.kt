package com.kinzlstanislav.topcontributors.feature.list.view.sorter

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ContributorsSorterTest {

    private companion object {
        val INPUT = listOf(
            Contributor("stanislav", "",1),
            Contributor("honza", "",5),
            Contributor("david", "",4),
            Contributor("petr", "",3),
            Contributor("roman", "",2)
        )

        val EXPECTED_RESULT = listOf(
            Contributor("honza", "",5),
            Contributor("david", "",4),
            Contributor("petr", "",3)
        )
    }

    private val subject = ContributorsSorter()

    @Test fun `sortFromTopByCommits()`() {
        assertThat(subject.sortFromTopByCommits(INPUT, 3))
            .isEqualTo(EXPECTED_RESULT)
    }

}