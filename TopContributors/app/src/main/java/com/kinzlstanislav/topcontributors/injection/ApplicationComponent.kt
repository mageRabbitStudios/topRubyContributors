package com.kinzlstanislav.topcontributors.injection

import android.app.Application
import com.kinzlstanislav.topcontributors.TopContributorsApp
import com.kinzlstanislav.topcontributors.architecture.injection.DomainModule
import com.kinzlstanislav.topcontributors.architecture.injection.NetworkModule
import com.kinzlstanislav.topcontributors.architecture.injection.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindings::class,
    FragmentBindings::class,
    DomainModule::class,
    RepositoryModule::class,
    NetworkModule::class
])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: TopContributorsApp)
}
