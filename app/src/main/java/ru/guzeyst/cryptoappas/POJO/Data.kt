package ru.guzeyst.cryptoappas.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data (
    @Expose @SerializedName("CoinInfo" ) val CoinInfo : CoinInfo? = null
)