package com.carvalho.monnosbroker.core.domain.models

data class Token(
    val id: String = "",
    val symbol: Symbol,
    val priceIndex: Float= 0f,
    val lastDayPriceIndexChangePercent: Float  = 0f,
    val counterAssetRank: Int = 0,
    val priceIndexString: String = "$priceIndex"
) {
    override fun equals(other: Any?): Boolean {
        return other is Token && symbol == other.symbol
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + symbol.hashCode()
        result = 31 * result + priceIndex.hashCode()
        result = 31 * result + lastDayPriceIndexChangePercent.hashCode()
        result = 31 * result + counterAssetRank
        result = 31 * result + priceIndexString.hashCode()
        return result
    }
}
