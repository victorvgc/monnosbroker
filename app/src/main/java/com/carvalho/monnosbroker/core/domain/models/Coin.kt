package com.carvalho.monnosbroker.core.domain.models

data class Coin(
    val name: String,
    val fullName: String = "",
    val icon: String = "",
    val blockchainUrl: String = "",
    var isFavorite: Boolean,
    val color: String = "",
    val totalSupply: Double = 0.0,
    val circulatingSupply: Double = 0.0,
) {
    val circulationPercent: Float = if (totalSupply != 0.0)
        ((circulatingSupply / totalSupply) * 100.0).toFloat()
    else
        0f

    override fun equals(other: Any?): Boolean {
        return other is Coin && other.name == name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + blockchainUrl.hashCode()
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + totalSupply.hashCode()
        result = 31 * result + circulatingSupply.hashCode()
        return result
    }
}