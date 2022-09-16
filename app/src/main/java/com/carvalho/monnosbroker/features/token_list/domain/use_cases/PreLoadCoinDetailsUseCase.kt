package com.carvalho.monnosbroker.features.token_list.domain.use_cases

import com.carvalho.monnosbroker.core.domain.repositories.CoinRepository

class PreLoadCoinDetailsUseCase(private val coinRepository: CoinRepository) {
    suspend operator fun invoke() {
        coinRepository.loadCoinsDetails()
    }
}