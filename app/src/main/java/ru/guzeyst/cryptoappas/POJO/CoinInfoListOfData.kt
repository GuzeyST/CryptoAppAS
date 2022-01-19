package ru.guzeyst.cryptoappas.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoListOfData (
    @Expose @SerializedName("Data") val Data : List<Data>? = null
)