package com.carvalho.monnosbroker.core.domain.repositories

import com.carvalho.monnosbroker.core.domain.models.Symbol
import com.carvalho.monnosbroker.core.domain.models.Token

interface TokenRepository {
    suspend fun fetchTokenList(): List<Token>

    suspend fun getTokenBySymbol(symbol: Symbol): Token?
}