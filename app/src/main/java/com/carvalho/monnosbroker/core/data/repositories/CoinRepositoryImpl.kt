package com.carvalho.monnosbroker.core.data.repositories

import com.carvalho.monnosbroker.core.domain.models.Coin
import com.carvalho.monnosbroker.core.domain.data_souces.CoinDataSource
import com.carvalho.monnosbroker.core.domain.repositories.CoinRepository

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