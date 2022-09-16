package com.carvalho.monnosbroker.features.data.data_sources

import com.carvalho.monnosbroker.core.models.Token
import com.carvalho.monnosbroker.features.data.services.TokenService
import com.carvalho.monnosbroker.features.domain.data_sources.TokenDataSource

class TokenRemoteDataSourceImpl(private val service: TokenService) : TokenDataSource.Remote {
    override suspend fun fetchTokenList(): List<Token> {
        val response = service.getTokenList()

        return if (response.isSuccessful) {
            response.body()?.let{ remote ->
                val appList = mutableListOf<Token>()
                remote.response?.forEach {
                    appList.add(it.toAppModel())
                }

                appList
            } ?: run {
                emptyList()
            }
        } else {
            emptyList()
        }
    }
}