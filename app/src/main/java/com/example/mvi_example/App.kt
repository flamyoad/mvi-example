package com.example.mvi_example

import android.app.Application
import com.example.mvi_example.di.apiModules
import com.example.mvi_example.di.mapperModules
import com.example.mvi_example.di.serviceModules
import com.example.mvi_example.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(
            apiModules,
            serviceModules,
            mapperModules,
            viewModelModules
        )

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModules)
        }
    }
}