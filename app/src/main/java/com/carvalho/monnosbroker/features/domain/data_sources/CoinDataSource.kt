package com.carvalho.monnosbroker.features.domain.data_sources

import com.carvalho.monnosbroker.core.models.Coin

interface CoinDataSource {
    interface Remote {
        suspend fun loadCoinsDetails(): List<Coin>
    }

    interface Local {
        suspend fun getCoin(name: String): Coin?

        suspend fun saveCoins(coinList: List<Coin>)

        suspend fun updateFavorite(coin: Coin)
    }
}