package com.carvalho.monnosbroker.core.data.room

import androidx.room.*
import com.carvalho.monnosbroker.core.data.models.LocalToken

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTokenList(tokenList: List<LocalToken>)

    @Query("SELECT * FROM token")
    suspend fun getTokenList(): List<LocalToken>?

    @Update
    suspend fun updateToken(localToken: LocalToken)

    @Query("SELECT * FROM token WHERE baseCurrency LIKE :tokenBaseCurrency AND counterCurrency LIKE :tokenCounterCurrency")
    suspend fun getToken(tokenBaseCurrency: String, tokenCounterCurrency: String): LocalToken?
}