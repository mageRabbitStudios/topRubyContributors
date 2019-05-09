package com.kinzlstanislav.topcontributors.injection

import com.kinzlstanislav.topcontributors.base.annotation.PerActivity
import com.kinzlstanislav.topcontributors.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindings {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun bindMainActivity(): MainActivity

}