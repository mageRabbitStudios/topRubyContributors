package com.kinzlstanislav.topcontributors.feature.list.view.sorter

import com.kinzlstanislav.topcontributors.architecture.core.model.Contributor

class ContributorsSorter {

    fun sortFromTopByCommits(input: List<Contributor>, howMany: Int): List<Contributor>
         = input.sortedBy { -it.numberOfCommits }.subList(0, howMany)

}