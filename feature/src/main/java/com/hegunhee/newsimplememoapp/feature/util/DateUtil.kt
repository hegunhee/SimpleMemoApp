package com.hegunhee.newsimplememoapp.feature.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

object DateUtil {

    private val calendar = Calendar.getInstance().apply {
        time = Date()
        add(Calendar.MONTH,1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getKoreaLocalTime() = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

    fun getYear() : Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getKoreaLocalTime().year
        }else {
            calendar.get(Calendar.YEAR)
        }
    }

    fun getMonth() : Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getKoreaLocalTime().monthValue
        }else {
            calendar.get(Calendar.MONTH)
        }
    }

    fun getHour() : Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getKoreaLocalTime().hour
        }else {
            calendar.get(Calendar.HOUR_OF_DAY)
        }
    }

    fun getMinute() : Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getKoreaLocalTime().minute
        }else {
            calendar.get(Calendar.MINUTE)
        }
    }
}