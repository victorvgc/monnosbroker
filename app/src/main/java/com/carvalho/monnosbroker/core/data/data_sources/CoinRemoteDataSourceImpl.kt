package com.carvalho.monnosbroker.core.data.data_sources

import com.carvalho.monnosbroker.core.domain.models.Coin
import com.carvalho.monnosbroker.core.data.services.CoinService
import com.carvalho.monnosbroker.core.domain.data_souces.CoinDataSource

class CoinRemoteDataSourceImpl(private val service: CoinService): CoinDataSource.Remote {
    override suspend fun loadCoinsDetails(): List<Coin> {
        val response = service.loadCoinsDetails()

        if (response.isSuccessful) {
            val result = response.body()

            result?.let { remoteResponse ->
                val coinList = mutableListOf<Coin>()

                remoteResponse.response?.forEach { remoteCoin ->
                    coinList.add(remoteCoin.toAppModel())
                }

                return coinList
            } ?: run {
                return emptyList()
            }
        }

        return emptyList()
    }
}