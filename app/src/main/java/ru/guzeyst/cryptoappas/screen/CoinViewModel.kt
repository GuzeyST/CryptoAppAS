package ru.guzeyst.cryptoappas.screen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.guzeyst.cryptoappas.POJO.CoinPriceInfo
import ru.guzeyst.cryptoappas.POJO.CoinPriceInfoRawData
import ru.guzeyst.cryptoappas.POJO.MY_TAG
import ru.guzeyst.cryptoappas.api.ApiFactory
import ru.guzeyst.cryptoappas.database.DataBase
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = DataBase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
        //Log.d(MY_TAG, "progress init")
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    private fun loadData() {
        val dis = ApiFactory.apiService.getTopCoinsInfo()
            .map { it.Data?.map { it.CoinInfo?.Name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFullPriceList(fsyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                //Log.d(MY_TAG, "Success: $it")
            }, {
                it.message?.let { it1 -> Log.d(MY_TAG, "Failure: $it1")}
            })
        compositeDisposable.add(dis)

    }

    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJSONObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyJsonSetKey = currencyJson.keySet()
            for (currencyKey in currencyJsonSetKey) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}