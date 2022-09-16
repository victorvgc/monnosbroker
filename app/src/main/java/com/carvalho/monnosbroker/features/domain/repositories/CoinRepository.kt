package com.carvalho.monnosbroker.features.domain.repositories

import com.carvalho.monnosbroker.core.models.Coin

interface CoinRepository {
    suspend fun loadCoinsDetails()

    suspend fun getCoin(name: String): Coin?

    suspend fun saveFavorite(coin: Coin)
}