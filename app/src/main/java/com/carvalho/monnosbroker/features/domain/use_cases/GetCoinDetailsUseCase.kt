package com.carvalho.monnosbroker.features.domain.use_cases

import com.carvalho.monnosbroker.core.models.Coin
import com.carvalho.monnosbroker.features.domain.repositories.CoinRepository

class GetCoinDetailsUseCase(private val coinRepository: CoinRepository) {
    suspend operator fun invoke(coinAbbreviation: String): Coin? {
        return coinRepository.getCoin(coinAbbreviation)
    }
}