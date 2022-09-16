package com.carvalho.monnosbroker.features.token_list.domain.use_cases

import com.carvalho.monnosbroker.core.domain.models.Coin
import com.carvalho.monnosbroker.core.domain.repositories.CoinRepository

class SetCoinAsFavoriteUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke(coin: Coin) {
        repository.saveFavorite(coin)
    }
}