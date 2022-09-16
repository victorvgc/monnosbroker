package com.carvalho.monnosbroker.core.domain.use_cases

import com.carvalho.monnosbroker.core.domain.models.Symbol
import com.carvalho.monnosbroker.core.domain.models.Token
import com.carvalho.monnosbroker.core.domain.repositories.TokenRepository

class GetTokenBySymbolUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(symbol: Symbol): Token? {
        return tokenRepository.getTokenBySymbol(symbol)
    }
}