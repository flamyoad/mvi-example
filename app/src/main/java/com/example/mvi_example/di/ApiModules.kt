package com.example.mvi_example.di

import com.example.mvi_example.BuildConfig.DEBUG
import com.example.mvi_example.api.MealApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val apiModules = module {
    single { provideMealApi(get()) }
    single { provideHttpClient() }
}

fun provideMealApi(httpClient: OkHttpClient): MealApi {
    val retrofit = Retrofit.Builder()
        .baseUrl(MealApi.BASE_URL)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    return retrofit.create(MealApi::class.java)
}

fun provideHttpClient(): OkHttpClient {
    val connectTimeout: Long = 60
    val readTimeout: Long = 60

    val okHttpClientBuilder = OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)

    if (DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    return okHttpClientBuilder.build()
}

