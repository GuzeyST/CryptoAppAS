package ru.guzeyst.cryptoappas.utils

import android.content.res.Resources
import ru.guzeyst.cryptoappas.R
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object{
        fun convertTimestampToTime(timestmp: Long?): String{
            if(timestmp == null) return Resources.getSystem().getString(R.string.void_data)
            val stamp = Timestamp(timestmp*1000)
            val data = Date(stamp.time)
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(data)
        }
    }
}