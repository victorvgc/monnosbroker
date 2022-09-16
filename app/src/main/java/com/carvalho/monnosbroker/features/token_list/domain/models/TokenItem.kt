package com.carvalho.monnosbroker.features.token_list.domain.models

import com.carvalho.monnosbroker.core.domain.models.Symbol
import com.carvalho.monnosbroker.core.domain.models.Token

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
