package com.carvalho.monnosbroker.features.data.repositories

import com.carvalho.monnosbroker.core.models.Token
import com.carvalho.monnosbroker.features.domain.data_sources.TokenDataSource
import com.carvalho.monnosbroker.features.domain.repositories.TokenRepository

class TokenRepositoryImpl(
    private val local: TokenDataSource.Local,
    private val remote: TokenDataSource.Remote
) : TokenRepository {
    override suspend fun fetchTokenList(): List<Token> {
        val list = remote.fetchTokenList()

        local.saveTokenList(list)

        return local.getTokenList()
    }
}