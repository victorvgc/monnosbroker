package com.carvalho.monnosbroker.core.data.models

import com.carvalho.monnosbroker.core.domain.models.Symbol
import com.carvalho.monnosbroker.core.domain.models.Token
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
            lastDayPriceIndexChangePercent,
            counterAssetRank,
            false
        )
    }
}
