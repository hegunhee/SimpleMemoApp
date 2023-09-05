package com.hegunhee.newsimplememoapp.feature.util

import java.util.Calendar
import java.util.Date

object DateUtil {

    private val calendar = Calendar.getInstance().apply {
        time = Date()
        add(Calendar.HOUR_OF_DAY,9)
        add(Calendar.MONTH,1)
    }
}