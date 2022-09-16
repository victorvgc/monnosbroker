package com.carvalho.monnosbroker.core.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carvalho.monnosbroker.core.domain.models.Coin

@Entity(tableName = "coinDetails")
data class LocalCoin(
    @PrimaryKey
    val name: String,
    val fullName: String,
    val icon: String = "",
    val blockChainUrl: String? = "",
    val marketType: String? = "",
    var isFavorite: Boolean,
    val color: String,
    val totalSupply: Double,
    val circulatingSupply: Double
) {
    companion object {
        fun fromAppModel(appModel: Coin): LocalCoin {
            return LocalCoin(
                name = appModel.name,
                fullName = appModel.fullName,
                icon = appModel.icon,
                isFavorite = appModel.isFavorite,
                color = appModel.color,
                blockChainUrl = appModel.blockchainUrl,
                totalSupply = appModel.totalSupply,
                circulatingSupply = appModel.circulatingSupply
            )
        }
    }

    fun toAppModel(): Coin {
        return Coin(
            name = name,
            fullName = fullName,
            icon = icon,
            isFavorite = isFavorite,
            color = color,
            blockchainUrl = blockChainUrl ?: "",
            totalSupply = totalSupply,
            circulatingSupply = circulatingSupply
        )
    }

    override fun equals(other: Any?): Boolean {
        return other is LocalCoin && other.name == this.name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + (blockChainUrl?.hashCode() ?: 0)
        result = 31 * result + (marketType?.hashCode() ?: 0)
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + color.hashCode()
        return result
    }
}