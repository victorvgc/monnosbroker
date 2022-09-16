package com.carvalho.monnosbroker.core.data.data_sources

import com.carvalho.monnosbroker.core.domain.models.Token
import com.carvalho.monnosbroker.core.data.services.TokenService
import com.carvalho.monnosbroker.core.domain.data_souces.TokenDataSource

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