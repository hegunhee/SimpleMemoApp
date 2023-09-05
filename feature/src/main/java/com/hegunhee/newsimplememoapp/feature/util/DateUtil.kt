package com.hegunhee.newsimplememoapp.feature.util

import android.os.Build
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

object DateUtil {

    private val calendar = Calendar.getInstance().apply {
        time = Date()
        add(Calendar.HOUR_OF_DAY,9)
        add(Calendar.MONTH,1)
    }

    fun getYear() : Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().year
        }else {
            calendar.get(Calendar.YEAR)
        }
    }

    fun getMonth() : Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().monthValue
        }else {
            calendar.get(Calendar.MONTH)
        }
    }
}