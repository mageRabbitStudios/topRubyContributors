package com.kinzlstanislav.topcontributors

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class TopContributorsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule).androidContext(this@TopContributorsApp)
        }
    }
}