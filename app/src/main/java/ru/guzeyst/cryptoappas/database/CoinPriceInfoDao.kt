package ru.guzeyst.cryptoappas.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.guzeyst.cryptoappas.POJO.CoinPriceInfo

@Dao
interface CoinPriceInfoDao {
    @Query("SELECT * FROM full_price_list ORDER BY LASTUPDATE DESC")
    fun getPriceList(): LiveData<List<CoinPriceInfo>>

    @Query("SELECT * FROM full_price_list WHERE FROMSYMBOL == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym: String): LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList: List<CoinPriceInfo>)

    @Query("DELETE FROM full_price_list")
    fun deleteAll()
}
