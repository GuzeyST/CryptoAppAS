package ru.guzeyst.cryptoappas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.guzeyst.cryptoappas.POJO.CoinPriceInfo
import ru.guzeyst.cryptoappas.POJO.DB_NAME

@Database(entities = [CoinPriceInfo::class], version = 2, exportSchema = false)
abstract class DataBase: RoomDatabase(){
    companion object{
        private var db: DataBase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): DataBase{
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    DataBase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
                db = instance
                return instance
            }
        }
    }

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}