package com.carvalho.monnosbroker.features.coin_details.view_models

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.carvalho.monnosbroker.core.domain.models.Coin
import com.carvalho.monnosbroker.core.domain.models.Symbol
import com.carvalho.monnosbroker.core.domain.models.Token
import com.carvalho.monnosbroker.core.domain.use_cases.GetCoinDetailsUseCase
import com.carvalho.monnosbroker.core.domain.use_cases.GetTokenBySymbolUseCase
import com.carvalho.monnosbroker.core.viewmodel.BaseState
import com.carvalho.monnosbroker.core.viewmodel.BaseViewModel
import com.carvalho.monnosbroker.features.coin_details.domain.models.CoinDetailsViewData
import com.carvalho.monnosbroker.features.coin_details.ui.CoinDetailsActivity
import com.carvalho.monnosbroker.features.token_list.domain.use_cases.SetCoinAsFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CoinDetailsViewModel(
    private val getCoinDetails: GetCoinDetailsUseCase,
    private val getToken: GetTokenBySymbolUseCase,
    private val setCoinAsFavorite: SetCoinAsFavoriteUseCase
) : BaseViewModel<CoinDetailsViewData>() {

    private lateinit var coinAbbreviation: String
    private lateinit var tokenCurrency: String

    private lateinit var coin: Coin
    private lateinit var baseCoin: Coin
    private lateinit var token: Token

    private var hasModifications = false

    override suspend fun observeData(bundle: Bundle?): Flow<BaseState<CoinDetailsViewData>> = flow {
        emit(BaseState.Loading())

        if (bundle != null && bundle.isEmpty.not()) {
            coinAbbreviation = bundle.getString(CoinDetailsActivity.COIN_ABBREVIATION, "")
            tokenCurrency = bundle.getString(CoinDetailsActivity.TOKEN_SYMBOL_BASE_CURRENCY, "")

            if (coinAbbreviation.isEmpty() || tokenCurrency.isEmpty()) {
                emit(BaseState.Failure("Empty values"))
            } else {
                val coin = getCoinDetails(coinAbbreviation)
                val token = getToken(Symbol(tokenCurrency, coinAbbreviation))

                if (coin != null && token != null) {
                    val baseCoin = getCoinDetails(token.symbol.baseCurrency)
                    if (baseCoin != null) {
                        this@CoinDetailsViewModel.coin = coin
                        this@CoinDetailsViewModel.baseCoin = baseCoin
                        this@CoinDetailsViewModel.token = token
                        emit(BaseState.Success(CoinDetailsViewData(baseCoin, coin, token)))
                    } else
                        emit(BaseState.Failure("BaseCoin not found"))
                } else {
                    emit(BaseState.Failure("Coin or Token not found"))
                }
            }
        } else {
            emit(BaseState.Failure("Empty bundle"))
        }
    }

    fun getCoinUrl(): String {
        return coin.blockchainUrl
    }

    fun getCoinName(): String {
        return coin.fullName
    }

    fun toggleFav(): Coin {
        hasModifications = hasModification().not()
        coin.isFavorite = coin.isFavorite.not()
        viewModelScope.launch {
            setCoinAsFavorite(coin)
        }
        return coin
    }

    fun hasModification(): Boolean {
        return hasModifications
    }
}