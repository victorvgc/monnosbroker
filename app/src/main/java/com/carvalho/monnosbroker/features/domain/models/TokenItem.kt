package com.carvalho.monnosbroker.features.domain.models

import com.carvalho.monnosbroker.core.models.Symbol
import com.carvalho.monnosbroker.core.models.Token

data class TokenItem(
    val currencyAbbreviation: String,
    var isFavorite: Boolean,
    val currencyName: String,
    val currencyIconUrl: String,
    val currencyIndex: String,
    val currencyIndexValue: String,
    val indexPercent: Float
) {
    fun toAppModel(): Token {
        return Token(
            symbol = Symbol(currencyAbbreviation, currencyIndex),
            isFavorite = isFavorite
        )
    }
}
