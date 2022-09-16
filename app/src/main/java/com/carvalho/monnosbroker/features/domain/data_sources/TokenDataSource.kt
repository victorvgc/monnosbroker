package com.carvalho.monnosbroker.features.domain.data_sources

import com.carvalho.monnosbroker.core.models.Token

interface TokenDataSource {
    interface Remote {
        suspend fun fetchTokenList(): List<Token>
    }

    interface Local {
        suspend fun saveTokenList(tokenList: List<Token>)

        suspend fun getTokenList(): List<Token>
    }
}