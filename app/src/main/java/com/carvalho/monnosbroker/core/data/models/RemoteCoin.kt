package com.carvalho.monnosbroker.core.data.models

import com.carvalho.monnosbroker.core.domain.models.Coin

data class RemoteCoin(
    val name: String = "",
    val fullName: String = "",
    val iconPng: String = "",
    val color: String? = "",
    val blockChainUrl: String? = "",
    val marketType: String? = "",
    val tags: List<String>? = emptyList(),
    val assetAboutInfo: AssetAboutInfo? = null
) {
    fun toAppModel(): Coin {
        return Coin(
            name =  name,
            fullName = fullName,
            icon = iconPng,
            isFavorite = false,
            color = color ?: "#FFFFFF",
            blockchainUrl = blockChainUrl ?: "",
            totalSupply = assetAboutInfo?.maxSupply?: 0.0,
            circulatingSupply = assetAboutInfo?.circulatingSupply ?: 0.0
        )
    }
}