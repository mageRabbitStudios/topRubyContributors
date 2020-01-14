package com.kinzlstanislav.topcontributors.feature.list.viewmodel

import com.kinzlstanislav.topcontributors.architecture.repository.model.Contributor
import com.kinzlstanislav.topcontributors.architecture.repository.model.User

val TEST_CONTRIBUTORS_25 = listOf(
    stubContributor(name = "Contributor1", commitNum = 1),
    stubContributor(name = "Contributor2", commitNum = 2),
    stubContributor(name = "Contributor3", commitNum = 3),
    stubContributor(name = "Contributor4", commitNum = 4),
    stubContributor(name = "Contributor5", commitNum = 5),
    stubContributor(name = "Contributor6", commitNum = 6),
    stubContributor(name = "Contributor7", commitNum = 7),
    stubContributor(name = "Contributor8", commitNum = 8),
    stubContributor(name = "Contributor9", commitNum = 9),
    stubContributor(name = "Contributor10", commitNum = 10),
    stubContributor(name = "Contributor11", commitNum = 11),
    stubContributor(name = "Contributor12", commitNum = 12),
    stubContributor(name = "Contributor13", commitNum = 13),
    stubContributor(name = "Contributor14", commitNum = 14),
    stubContributor(name = "Contributor15", commitNum = 15),
    stubContributor(name = "Contributor16", commitNum = 16),
    stubContributor(name = "Contributor17", commitNum = 17),
    stubContributor(name = "Contributor18", commitNum = 18),
    stubContributor(name = "Contributor19", commitNum = 19),
    stubContributor(name = "Contributor20", commitNum = 20),
    stubContributor(name = "Contributor21", commitNum = 21),
    stubContributor(name = "Contributor22", commitNum = 22),
    stubContributor(name = "Contributor23", commitNum = 23),
    stubContributor(name = "Contributor24", commitNum = 24),
    stubContributor(name = "Contributor25", commitNum = 25)
)

val EXPECTED_TEST_CONTRIBUTORS_20 = listOf(
    stubContributor(name = "Contributor25", commitNum = 25),
    stubContributor(name = "Contributor24", commitNum = 24),
    stubContributor(name = "Contributor23", commitNum = 23),
    stubContributor(name = "Contributor22", commitNum = 22),
    stubContributor(name = "Contributor21", commitNum = 21),
    stubContributor(name = "Contributor20", commitNum = 20),
    stubContributor(name = "Contributor19", commitNum = 19),
    stubContributor(name = "Contributor18", commitNum = 18),
    stubContributor(name = "Contributor17", commitNum = 17),
    stubContributor(name = "Contributor16", commitNum = 16),
    stubContributor(name = "Contributor15", commitNum = 15),
    stubContributor(name = "Contributor14", commitNum = 14),
    stubContributor(name = "Contributor13", commitNum = 13),
    stubContributor(name = "Contributor12", commitNum = 12),
    stubContributor(name = "Contributor11", commitNum = 11),
    stubContributor(name = "Contributor10", commitNum = 10),
    stubContributor(name = "Contributor9", commitNum = 9),
    stubContributor(name = "Contributor8", commitNum = 8),
    stubContributor(name = "Contributor7", commitNum = 7),
    stubContributor(name = "Contributor6", commitNum = 6)
)

val SOME_CONTRIBUTOR_NAME = "Contributor"
val SOME_CONTRIBUTOR = stubContributor(SOME_CONTRIBUTOR_NAME, 0)

val SOME_USER = User("User", "Some Address")

private fun stubContributor(name: String, commitNum: Int) = Contributor(name, "", commitNum)