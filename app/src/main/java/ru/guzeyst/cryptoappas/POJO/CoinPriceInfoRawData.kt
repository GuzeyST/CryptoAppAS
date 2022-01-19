package ru.guzeyst.cryptoappas.POJO

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinPriceInfoRawData(
    @Expose @SerializedName("RAW") val coinPriceInfoJSONObject: JsonObject? = null
)