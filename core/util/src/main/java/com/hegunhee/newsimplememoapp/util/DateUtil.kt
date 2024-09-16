package com.hegunhee.newsimplememoapp.util

import java.time.LocalDateTime
import java.time.ZoneId

object DateUtil {

    private fun getKoreaLocalTime() = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

    fun getYear(): Int {
        return getKoreaLocalTime().year
    }

    fun getMonth(): Int {
        return getKoreaLocalTime().monthValue
    }

    fun getHour(): Int {
        return getKoreaLocalTime().hour
    }

    fun getMinute(): Int {
        return getKoreaLocalTime().minute
    }
}