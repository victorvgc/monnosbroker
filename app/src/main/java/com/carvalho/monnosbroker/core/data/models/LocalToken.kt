package com.carvalho.monnosbroker.core.data.models

import androidx.room.Entity
import com.carvalho.monnosbroker.core.domain.models.Symbol
import com.carvalho.monnosbroker.core.domain.models.Token

@Entity(tableName = "token", primaryKeys = ["baseCurrency", "counterCurrency"])
data class LocalToken(
    val id: String = "",
    val baseCurrency: String,
    val counterCurrency: String,
    val priceIndex: Float,
    val lastDayPriceIndexChangePercent: Float,
    val counterAssetRank: Int
) {
    companion object {
        fun fromAppModel(appModel: Token): LocalToken {
            return LocalToken(
                appModel.id,
                appModel.symbol.baseCurrency,
                appModel.symbol.counterCurrency,
                appModel.priceIndex,
                appModel.lastDayPriceIndexChangePercent,
                appModel.counterAssetRank
            )
        }
    }

    fun toAppModel(): Token {
        return Token(
            id,
            Symbol(baseCurrency, counterCurrency),
            priceIndex,
            lastDayPriceIndexChangePercent,
            counterAssetRank
        )
    }
}