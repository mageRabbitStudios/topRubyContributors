package com.kinzlstanislav.topcontributors.injection

import com.kinzlstanislav.topcontributors.architecture.core.dagger.scopes.PerFragment
import com.kinzlstanislav.topcontributors.feature.list.injection.ContributorsListModule
import com.kinzlstanislav.topcontributors.feature.list.view.FragmentContributorsList
import com.kinzlstanislav.topcontributors.feature.map.view.FragmentContributorMap
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBindings {

    @PerFragment
    @ContributesAndroidInjector(modules = [ContributorsListModule::class])
    fun bindContributorsListFragment(): FragmentContributorsList


    @PerFragment
    @ContributesAndroidInjector
    fun bindContributorMapFragment(): FragmentContributorMap

}