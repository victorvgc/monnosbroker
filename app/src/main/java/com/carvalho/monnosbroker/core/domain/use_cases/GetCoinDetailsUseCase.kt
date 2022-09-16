package com.carvalho.monnosbroker.core.domain.use_cases

import com.carvalho.monnosbroker.core.domain.models.Coin
import com.carvalho.monnosbroker.core.domain.repositories.CoinRepository

class GetCoinDetailsUseCase(private val coinRepository: CoinRepository) {
    suspend operator fun invoke(coinAbbreviation: String): Coin? {
        return coinRepository.getCoin(coinAbbreviation)
    }
}