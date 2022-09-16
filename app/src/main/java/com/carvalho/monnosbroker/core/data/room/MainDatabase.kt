package com.carvalho.monnosbroker.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carvalho.monnosbroker.BuildConfig
import com.carvalho.monnosbroker.core.data.models.LocalCoin
import com.carvalho.monnosbroker.core.data.models.LocalToken

@Database(entities = [LocalCoin::class, LocalToken::class], version = BuildConfig.VERSION_CODE, exportSchema = false)
abstract class MainDatabase : RoomDatabase(){
    abstract fun coinDao(): CoinDao
    abstract fun tokenDao(): TokenDao
}