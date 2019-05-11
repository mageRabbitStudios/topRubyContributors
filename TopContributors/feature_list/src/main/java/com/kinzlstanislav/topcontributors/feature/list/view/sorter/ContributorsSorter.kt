package com.kinzlstanislav.topcontributors.feature.list.view.sorter

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor
import javax.inject.Inject

class ContributorsSorter @Inject constructor() {

    fun sortFromTopByCommits(input: List<Contributor>, howMany: Int): List<Contributor>
         = input.sortedBy { -it.numberOfCommits }.subList(0, howMany)

}