package com.kinzlstanislav.topcontributors.dependencyinjection

import com.kinzlstanislav.topcontributors.base.annotation.PerActivity
import com.kinzlstanislav.topcontributors.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindings {

    @PerActivity
    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity

}