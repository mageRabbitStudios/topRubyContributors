package com.kinzlstanislav.topcontributors.injection

import com.kinzlstanislav.topcontributors.architecture.core.dagger.scopes.PerActivity
import com.kinzlstanislav.topcontributors.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindings {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun bindMainActivity(): MainActivity

}