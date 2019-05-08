package com.kinzlstanislav.topcontributors.dependencyinjection

import com.kinzlstanislav.topcontributors.base.annotation.PerFragment
import com.kinzlstanislav.topcontributors.list.view.FragmentContributorsList
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBindings {

    @PerFragment
    @ContributesAndroidInjector
    fun bindContributorsListFragment(): FragmentContributorsList

}