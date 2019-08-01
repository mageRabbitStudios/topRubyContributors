package com.kinzlstanislav.topcontributors

import android.location.Geocoder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kinzlstanislav.topcontributors.architecture.network.GitHubRestData
import com.kinzlstanislav.topcontributors.architecture.network.api.GithubApiService
import com.kinzlstanislav.topcontributors.architecture.repository.ContributorsRepository
import com.kinzlstanislav.topcontributors.architecture.repository.UserRepository
import com.kinzlstanislav.topcontributors.architecture.repository.mapper.ContributorsResponseMapper
import com.kinzlstanislav.topcontributors.architecture.repository.mapper.UserResponseMapper
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import com.kinzlstanislav.topcontributors.ui.imageloading.GlideImageLoader
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    // view model
    single { ContributorsListViewModel(
        geocoder = get(),
        userRepository = get(),
        contributorsRepository = get()) }

    // repository
    factory { ContributorsRepository(get(), get()) }
    factory { UserRepository(get(), get()) }

    // mapper
    factory { ContributorsResponseMapper() }
    factory { UserResponseMapper() }

    // network
    factory { Retrofit.Builder()
        .baseUrl(GitHubRestData.REST_GITHUB_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(get()) //okhttp client
        .build()
        .create(GithubApiService::class.java)
    }

    factory<Interceptor> { // logger
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    factory { // okhttp client
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(get())
        }
        builder.build()
    }

    // other
    single { Geocoder(androidContext()) }
    single { GlideImageLoader() }
}