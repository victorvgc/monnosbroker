package com.carvalho.monnosbroker.features.data.models

import androidx.room.Entity
import com.carvalho.monnosbroker.core.models.Symbol
import com.carvalho.monnosbroker.core.models.Token
import java.util.*

@Entity(tableName = "token", primaryKeys = ["baseCurrency", "counterCurrency"])
data class LocalToken(
    val id: String = "",
    val baseCurrency: String,
    val counterCurrency: String,
    val priceIndexString: String? = "",
    val priceIndex: Float,
    val lastDayPriceIndexChangePercent: Float,
    val lastWeekPriceIndexChangePercent: Float? = 0.0f,
    val lastMonthPriceIndexChangePercent: Float? = 0.0f,
    val counterAssetRank: Int,
    val date: Long,
    var isFavorite: Boolean
) {
    companion object {
        fun fromAppModel(appModel: Token): LocalToken {
            return LocalToken(
                appModel.id,
                appModel.symbol.baseCurrency,
                appModel.symbol.counterCurrency,
                appModel.priceIndexString,
                appModel.priceIndex,
                appModel.lastDayPriceIndexChangePercent,
                appModel.lastWeekPriceIndexChangePercent,
                appModel.lastMonthPriceIndexChangePercent,
                appModel.counterAssetRank,
                appModel.date.time,
                appModel.isFavorite
            )
        }
    }

    fun toAppModel(): Token {
        return Token(
            id,
            Symbol(baseCurrency, counterCurrency),
            priceIndex,
            priceIndexString,
            lastDayPriceIndexChangePercent,
            lastWeekPriceIndexChangePercent,
            lastMonthPriceIndexChangePercent,
            counterAssetRank,
            Date(date),
            isFavorite
        )
    }
}