package com.carvalho.monnosbroker.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carvalho.monnosbroker.features.data.models.LocalCoin
import com.carvalho.monnosbroker.features.data.models.LocalToken
import com.carvalho.monnosbroker.features.data.room.CoinDao
import com.carvalho.monnosbroker.features.data.room.TokenDao

@Database(entities = [LocalCoin::class, LocalToken::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase(){
    abstract fun coinDao(): CoinDao
    abstract fun tokenDao(): TokenDao
}