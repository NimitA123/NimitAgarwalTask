package com.example.nimitagarwaltask.domain.model


import com.google.gson.annotations.SerializedName

data class HoldingsResponse(
    @SerializedName("data")
    val `data`: Data? = Data()
) {
    data class Data(
        @SerializedName("userHolding")
        val userHolding: List<UserHolding?>? = listOf()
    ) {
        data class UserHolding(
            @SerializedName("avgPrice")
            val avgPrice: Double? = 0.0,
            @SerializedName("close")
            val close: Double? = 0.0,
            @SerializedName("ltp")
            val ltp: Double? = 0.0,
            @SerializedName("quantity")
            val quantity: Int? = 0,
            @SerializedName("symbol")
            val symbol: String? = ""
        )
    }
}