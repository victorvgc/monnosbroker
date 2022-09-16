package com.carvalho.monnosbroker.features.domain.use_cases

import com.carvalho.monnosbroker.core.models.Coin
import com.carvalho.monnosbroker.features.domain.repositories.CoinRepository

class SetCoinAsFavoriteUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(coin: Coin) {
        repository.saveFavorite(coin)
    }
}