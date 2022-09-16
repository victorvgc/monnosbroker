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
            symbol = Symbol(currencyAbbreviation, currencyIndex)
        )
    }

    override fun equals(other: Any?): Boolean {
        return other is TokenItem && other.currencyAbbreviation == currencyAbbreviation
    }

    override fun hashCode(): Int {
        var result = currencyAbbreviation.hashCode()
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + currencyName.hashCode()
        result = 31 * result + currencyIconUrl.hashCode()
        result = 31 * result + currencyIndex.hashCode()
        result = 31 * result + currencyIndexValue.hashCode()
        result = 31 * result + indexPercent.hashCode()
        return result
    }
}
