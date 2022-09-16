package com.carvalho.monnosbroker.core.data.data_sources

import com.carvalho.monnosbroker.core.data.models.LocalCoin
import com.carvalho.monnosbroker.core.data.room.CoinDao
import com.carvalho.monnosbroker.core.domain.data_souces.CoinDataSource
import com.carvalho.monnosbroker.core.domain.models.Coin

class CoinLocalDataSourceImpl(private val coinDao: CoinDao) : CoinDataSource.Local {
    override suspend fun getCoin(name: String): Coin? {
        return coinDao.getCoinByName(name)?.toAppModel()
    }

    override suspend fun saveCoins(coinList: List<Coin>) {
        val newItemList = mutableListOf<LocalCoin>()
        val toBeSavedList = mutableListOf<LocalCoin>()

        coinList.forEach {
            newItemList.add(LocalCoin.fromAppModel(it))
        }

        toBeSavedList.addAll(newItemList)
        val savedList = coinDao.getAllCoins()

        for (newItem in newItemList) {
            if (savedList.isNullOrEmpty().not()) {
                for (saved in savedList!!) {
                    if (newItem == saved) {
                        val isFav = saved.isFavorite
                        newItem.isFavorite = isFav
                        coinDao.updateCoin(newItem)
                        toBeSavedList.remove(newItem)
                    }
                }
            }
        }

        if (toBeSavedList.isNotEmpty())
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