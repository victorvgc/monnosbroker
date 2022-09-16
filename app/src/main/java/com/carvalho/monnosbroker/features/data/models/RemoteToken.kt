package com.carvalho.monnosbroker.features.data.models

import com.carvalho.monnosbroker.core.models.Symbol
import com.carvalho.monnosbroker.core.models.Token
import java.sql.Timestamp

data class RemoteToken(
    val id: String?,
    val symbol: Symbol,
    val priceIndex: Float,
    val priceIndexString: String? = "",
    val lastDayPriceIndexChangePercent: Float,
    val lastWeekPriceIndexChangePercent: Float? = 0.0f,
    val lastMonthPriceIndexChangePercent: Float? = 0.0f,
    val counterAssetRank: Int,
    val date: Timestamp
) {
    fun toAppModel(): Token {
        return Token(
            id = id  ?: "",
            symbol = symbol,
            priceIndex,
            priceIndexString,
            lastDayPriceIndexChangePercent,
            lastWeekPriceIndexChangePercent,
            lastMonthPriceIndexChangePercent,
            counterAssetRank,
            date = date,
            false
        )
    }
}
