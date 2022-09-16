package com.carvalho.monnosbroker.core.domain.models

data class Symbol(
    val baseCurrency: String,
    val counterCurrency: String
) {
    override fun equals(other: Any?): Boolean {
        return other is Symbol
                && other.baseCurrency == baseCurrency && other.counterCurrency == counterCurrency
    }
}