package com.carvalho.monnosbroker.core.data.room

import androidx.room.*
import com.carvalho.monnosbroker.core.data.models.LocalCoin

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coinList: List<LocalCoin>)

    @Query("SELECT * FROM coinDetails WHERE name LIKE :coinAbbreviation")
    suspend fun getCoinByName(coinAbbreviation: String): LocalCoin?

    @Query("SELECT * FROM coinDetails")
    suspend fun getAllCoins(): List<LocalCoin>?

    @Update
    suspend fun updateCoin(coin: LocalCoin)
}