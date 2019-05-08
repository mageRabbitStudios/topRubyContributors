package com.kinzlstanislav.topcontributors

import android.app.Activity
import android.app.Application
import com.kinzlstanislav.topcontributors.dependencyinjection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class TopContributorsApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    open fun initializeDagger() {
        DaggerApplicationComponent.builder().application(this).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

}