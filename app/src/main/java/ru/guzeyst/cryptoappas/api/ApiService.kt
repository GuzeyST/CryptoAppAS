package ru.guzeyst.cryptoappas.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.guzeyst.cryptoappas.POJO.API_KEY
import ru.guzeyst.cryptoappas.POJO.CoinInfoListOfData
import ru.guzeyst.cryptoappas.POJO.CoinPriceInfoRawData
import ru.guzeyst.cryptoappas.POJO.TO_COIN

interface ApiService {
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey:String = API_KEY,
        @Query(QUERY_PARAM_LIMIT) limit:Int = 10,
        @Query(QUERY_PARAM_TO_SYM) tsym:String =  TO_COIN
    ): Single<CoinInfoListOfData>


    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey:String = API_KEY,
        @Query(QUERY_PARAM_FROM_SYMS) fsyms: String,
        @Query(QUERY_PARAM_TO_SYMS) tsyms: String = "USD"
    ): Single<CoinPriceInfoRawData>

    companion object{
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYM = "tsym"
        private const val QUERY_PARAM_FROM_SYMS = "fsyms"
        private const val QUERY_PARAM_TO_SYMS = "tsyms"
    }
}