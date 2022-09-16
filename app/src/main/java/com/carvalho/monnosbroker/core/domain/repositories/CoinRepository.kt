package com.carvalho.monnosbroker.core.domain.repositories

import com.carvalho.monnosbroker.core.domain.models.Coin

interface CoinRepository {
    suspend fun loadCoinsDetails()

    suspend fun getCoin(name: String): Coin?

    suspend fun saveFavorite(coin: Coin)
}