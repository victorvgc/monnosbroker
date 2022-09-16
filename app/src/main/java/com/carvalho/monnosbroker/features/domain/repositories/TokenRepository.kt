package com.carvalho.monnosbroker.features.domain.repositories

import com.carvalho.monnosbroker.core.models.Token

interface TokenRepository {
    suspend fun fetchTokenList(): List<Token>
}