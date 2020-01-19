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
import com.kinzlstanislav.topcontributors.base.imageloading.GlideImageLoader
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    // view model
    factory { ContributorsListViewModel(
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
        .client(
            OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                }
            }.build()
        )
        .build()
        .create(GithubApiService::class.java)
    }

    // other
    single { Geocoder(androidContext()) }
    single { GlideImageLoader() }
}