package com.carvalho.monnosbroker.features.domain.use_cases

import com.carvalho.monnosbroker.features.domain.repositories.CoinRepository

class PreLoadCoinDetailsUseCase(private val coinRepository: CoinRepository) {
    suspend operator fun invoke() {
        coinRepository.loadCoinsDetails()
    }
}