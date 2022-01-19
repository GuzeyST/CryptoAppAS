package ru.guzeyst.cryptoappas.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfo (
    @Expose @SerializedName("Name"               ) val Name               : String? = null,
)