package com.carvalho.monnosbroker.core.domain.data_souces

import com.carvalho.monnosbroker.core.domain.models.Symbol
import com.carvalho.monnosbroker.core.domain.models.Token

interface TokenDataSource {
    interface Remote {
        suspend fun fetchTokenList(): List<Token>
    }

    interface Local {
        suspend fun saveTokenList(tokenList: List<Token>)

        suspend fun getTokenList(): List<Token>

        suspend fun getTokenBySymbol(symbol: Symbol): Token?
    }
}