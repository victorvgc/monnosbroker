package com.carvalho.monnosbroker.core.models

import java.util.*

data class Token(
    val id: String = "",
    val symbol: Symbol,
    val priceIndex: Float= 0f,
    val priceIndexString: String? = "",
    val lastDayPriceIndexChangePercent: Float  = 0f,
    val lastWeekPriceIndexChangePercent: Float? = 0f,
    val lastMonthPriceIndexChangePercent: Float? = 0f,
    val counterAssetRank: Int = 0,
    val date: Date = Date(),
    var isFavorite: Boolean
)
