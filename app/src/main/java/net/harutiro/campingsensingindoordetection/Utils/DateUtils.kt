package net.harutiro.campingsensingindoordetection.Utils

import android.icu.text.SimpleDateFormat
import java.util.*

class DateUtils {

    fun getNowDate(): String? {
        val df = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
        val date = Date(System.currentTimeMillis())
        return df.format(date)
    }
}