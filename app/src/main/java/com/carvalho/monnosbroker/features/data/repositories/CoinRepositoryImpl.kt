package com.carvalho.monnosbroker.features.data.repositories

import com.carvalho.monnosbroker.core.models.Coin
import com.carvalho.monnosbroker.features.domain.data_sources.CoinDataSource
import com.carvalho.monnosbroker.features.domain.repositories.CoinRepository

class CoinRepositoryImpl(
    private val localDataSource: CoinDataSource.Local,
    private val remoteDataSource: CoinDataSource.Remote
) : CoinRepository {

    override suspend fun loadCoinsDetails() {
        val list = remoteDataSource.loadCoinsDetails()

        localDataSource.saveCoins(list)
    }

    override suspend fun getCoin(name: String): Coin? {
        return localDataSource.getCoin(name)
    }

    override suspend fun saveFavorite(coin: Coin) {
        localDataSource.updateFavorite(coin)
    }
}