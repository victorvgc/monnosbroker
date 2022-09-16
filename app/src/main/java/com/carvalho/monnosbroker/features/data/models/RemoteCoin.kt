package com.carvalho.monnosbroker.features.data.models

import com.carvalho.monnosbroker.core.models.Coin

data class RemoteCoin(
    val name: String = "",
    val fullName: String = "",
    val iconPng: String = "",
    val colorHex: String? = "",
    val blockChainUrl: String? = "",
    val marketType: String? = "",
    val tags: List<String>? = emptyList()
) {
    fun toAppModel(): Coin {
        return Coin(
            name =  name,
            fullName = fullName,
            icon = iconPng,
            isFavorite = false
        )
    }
}