package com.carvalho.monnosbroker.core.di

import androidx.room.Room
import com.carvalho.monnosbroker.core.data.MainDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    //  local
    single {
        Room.databaseBuilder(
            androidContext(),
            MainDatabase::class.java,
            "mainDb"
        ).build()
    }
}