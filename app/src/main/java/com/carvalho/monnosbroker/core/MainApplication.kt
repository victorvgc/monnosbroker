package com.carvalho.monnosbroker.core

import android.app.Application
import com.carvalho.monnosbroker.core.di.mainModule
import com.carvalho.monnosbroker.features.di.tokenListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                mainModule,
                tokenListModule
            )
        }
    }
}