package com.carvalho.monnosbroker.core.models

data class Coin(
    val name: String,
    val fullName: String = "",
    val icon: String = "",
    var isFavorite: Boolean
)