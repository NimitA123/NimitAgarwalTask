package com.example.nimitagarwaltask.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nimitagarwaltask.domain.model.HoldingsResponse


@Entity(tableName = "holdings")
data class HoldingsEntity(
    @PrimaryKey
    val symbol: String,
    val quantity: Int,
    val avgPrice: Double,
    val ltp: Double,
    val close: Double
)

fun HoldingsResponse.Data.UserHolding.toEntity(): HoldingsEntity {
    return HoldingsEntity(
        symbol = this.symbol ?: "",
        quantity = this.quantity ?: 0,
        avgPrice = this.avgPrice ?: 0.0,
        ltp = this.ltp ?: 0.0,
        close = this.close ?: 0.0
    )
}

