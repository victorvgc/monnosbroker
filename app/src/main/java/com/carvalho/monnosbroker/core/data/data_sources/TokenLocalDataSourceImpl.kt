package com.carvalho.monnosbroker.core.data.data_sources

import com.carvalho.monnosbroker.core.domain.models.Token
import com.carvalho.monnosbroker.core.data.models.LocalToken
import com.carvalho.monnosbroker.core.data.room.TokenDao
import com.carvalho.monnosbroker.core.domain.data_souces.TokenDataSource
import com.carvalho.monnosbroker.core.domain.models.Symbol

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

    override suspend fun getTokenBySymbol(symbol: Symbol): Token? {
        val localToken = tokenDao.getToken(symbol.baseCurrency, symbol.counterCurrency)

        return localToken?.toAppModel()
    }
}