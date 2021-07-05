package com.example.mvi_example.di

import com.example.mvi_example.core.network.NetworkRequestManager
import com.example.mvi_example.service.MealService
import org.koin.dsl.module

val serviceModules = module {
    single { provideRetrofitResponseParser() }
    single { MealService(get(), get(), get()) }
}

fun provideRetrofitResponseParser(): NetworkRequestManager {
    return NetworkRequestManager()
}