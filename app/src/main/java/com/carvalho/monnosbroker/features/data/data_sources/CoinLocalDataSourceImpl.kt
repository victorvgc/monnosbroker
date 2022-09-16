package com.carvalho.monnosbroker.features.data.data_sources

import com.carvalho.monnosbroker.core.models.Coin
import com.carvalho.monnosbroker.features.data.models.LocalCoin
import com.carvalho.monnosbroker.features.data.room.CoinDao
import com.carvalho.monnosbroker.features.domain.data_sources.CoinDataSource

class CoinLocalDataSourceImpl(private val coinDao: CoinDao) : CoinDataSource.Local {
    override suspend fun getCoin(name: String): Coin? {
        return coinDao.getCoinByName(name)?.toAppModel()
    }

    override suspend fun saveCoins(coinList: List<Coin>) {
        val localList = mutableListOf<LocalCoin>()
        val toBeSavedList = mutableListOf<LocalCoin>()

        coinList.forEach {
            localList.add(LocalCoin.fromAppModel(it))
        }

        toBeSavedList.addAll(localList)
        val savedList = coinDao.getAllCoins()

        for (local in localList) {
            if (savedList.isNullOrEmpty().not()) {
                for (saved in savedList!!){
                    if (local == saved) {
                        local.isFavorite = saved.isFavorite
                        coinDao.updateCoin(local)
                        toBeSavedList.remove(local)
                    }
                }
            }
        }

        coinDao.insertCoin(toBeSavedList)
    }

    override suspend fun updateFavorite(coin: Coin) {
        val local = coinDao.getCoinByName(coin.name)
        local?.apply {
            isFavorite = coin.isFavorite
            coinDao.updateCoin(this)
        }
    }
}