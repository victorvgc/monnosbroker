package com.carvalho.monnosbroker.core.domain.models

data class Token(
    val id: String = "",
    val symbol: Symbol,
    val priceIndex: Float= 0f,
    val lastDayPriceIndexChangePercent: Float  = 0f,
    val counterAssetRank: Int = 0,
    var isFavorite: Boolean,
    val priceIndexString: String = "$priceIndex"
)
