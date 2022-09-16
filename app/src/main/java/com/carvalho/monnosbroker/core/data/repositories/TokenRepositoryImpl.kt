package com.carvalho.monnosbroker.core.data.repositories

import com.carvalho.monnosbroker.core.domain.models.Token
import com.carvalho.monnosbroker.core.domain.data_souces.TokenDataSource
import com.carvalho.monnosbroker.core.domain.models.Symbol
import com.carvalho.monnosbroker.core.domain.repositories.TokenRepository

class TokenRepositoryImpl(
    private val local: TokenDataSource.Local,
    private val remote: TokenDataSource.Remote
) : TokenRepository {
    override suspend fun fetchTokenList(): List<Token> {
        val list = remote.fetchTokenList()

        local.saveTokenList(list)

        return local.getTokenList()
    }

    override suspend fun getTokenBySymbol(symbol: Symbol): Token? {
        return local.getTokenBySymbol(symbol)
    }
}