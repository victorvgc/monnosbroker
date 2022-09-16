package com.carvalho.monnosbroker.features.data.data_sources

import com.carvalho.monnosbroker.core.models.Token
import com.carvalho.monnosbroker.features.data.models.LocalToken
import com.carvalho.monnosbroker.features.data.room.TokenDao
import com.carvalho.monnosbroker.features.domain.data_sources.TokenDataSource

class TokenLocalDataSourceImpl(private val tokenDao: TokenDao) : TokenDataSource.Local {
    override suspend fun saveTokenList(tokenList: List<Token>) {
        val localList = mutableListOf<LocalToken>()

        tokenList.forEach {
            localList.add(LocalToken.fromAppModel(it))
        }

        tokenDao.saveTokenList(localList)
    }

    override suspend fun getTokenList(): List<Token> {
        val localList = tokenDao.getTokenList()

        return localList?.let { local ->
            val appList = mutableListOf<Token>()

            local.forEach {
                appList.add(it.toAppModel())
            }

            appList
        } ?: run {
            emptyList()
        }
    }
}