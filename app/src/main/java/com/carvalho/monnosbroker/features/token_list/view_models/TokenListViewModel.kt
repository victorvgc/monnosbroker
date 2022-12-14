package com.carvalho.monnosbroker.features.token_list.view_models

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.carvalho.monnosbroker.core.domain.models.Coin
import com.carvalho.monnosbroker.core.viewmodel.BaseState
import com.carvalho.monnosbroker.core.viewmodel.BaseViewModel
import com.carvalho.monnosbroker.features.token_list.domain.models.TokenItem
import com.carvalho.monnosbroker.core.domain.use_cases.GetCoinDetailsUseCase
import com.carvalho.monnosbroker.features.token_list.domain.use_cases.PreLoadCoinDetailsUseCase
import com.carvalho.monnosbroker.features.token_list.domain.use_cases.RefreshTokenListUseCase
import com.carvalho.monnosbroker.features.token_list.domain.use_cases.SetCoinAsFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TokenListViewModel(
    private val preLoadCoinDetails: PreLoadCoinDetailsUseCase,
    private val getCoinDetails: GetCoinDetailsUseCase,
    private val refreshTokenList: RefreshTokenListUseCase,
    private val setCoinAsFavorite: SetCoinAsFavoriteUseCase
) : BaseViewModel<List<TokenItem>>() {

    private var preLoadedList: List<TokenItem> = emptyList()

    init {
        viewModelScope.launch {
            preLoadCoinDetails()
        }
    }

    override suspend fun observeData(bundle: Bundle?): Flow<BaseState<List<TokenItem>>> =
        refreshTokenList().map { state ->
            when (state) {
                is BaseState.Failure -> BaseState.Failure(state.msg)
                is BaseState.Loading -> {
                    BaseState.Loading(preLoadedList)
                }
                is BaseState.Success -> {
                    val viewList = mutableListOf<TokenItem>()

                    state.data.forEach { item ->

                        val coin = getCoinDetails(item.symbol.counterCurrency)

                        coin?.let {
                            viewList.add(
                                TokenItem(
                                    currencyAbbreviation = item.symbol.counterCurrency,
                                    isFavorite = coin.isFavorite,
                                    currencyName = coin.fullName,
                                    currencyIconUrl = coin.icon,
                                    currencyIndex = item.symbol.baseCurrency,
                                    currencyIndexValue = item.priceIndex.toString(),
                                    indexPercent = item.lastDayPriceIndexChangePercent
                                )
                            )
                        }
                    }

                    val favList = mutableListOf<TokenItem>()
                    val normalList = mutableListOf<TokenItem>()
                    val endList = mutableListOf<TokenItem>()

                    viewList.forEach {
                        if (it.isFavorite)
                            favList.add(it)
                        else {
                            normalList.add(it)
                        }
                    }

                    endList.addAll(favList)
                    endList.addAll(normalList)

                    preLoadedList = endList
                    BaseState.Success(endList)
                }
            }
        }

    fun toggleFavoriteToken(tokenItem: TokenItem) {
        viewModelScope.launch {
            setCoinAsFavorite(
                Coin(
                    name = tokenItem.currencyAbbreviation,
                    isFavorite = tokenItem.isFavorite,
                )
            )
        }
    }
}