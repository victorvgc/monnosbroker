package com.carvalho.monnosbroker.features.coin_details.domain.models

import com.carvalho.monnosbroker.core.domain.models.Coin
import com.carvalho.monnosbroker.core.domain.models.Token

data class CoinDetailsViewData(
    val baseCoin: Coin,
    val coin: Coin,
    val token: Token
)
