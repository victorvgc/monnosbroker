package com.carvalho.monnosbroker.core.domain.models

data class Coin(
    val name: String,
    val fullName: String = "",
    val icon: String = "",
    val blockchainUrl: String = "",
    var isFavorite: Boolean,
    val color: String = "",
    val totalSupply: Double = 0.0,
    val circulatingSupply: Double = 0.0
)