package com.hegunhee.newsimplememoapp.feature.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

object DateUtil {

    private val calendar = Calendar.getInstance().apply {
        time = Date()
        add(Calendar.MONTH,1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getKoreaLocalTime() = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

    private val koreanDayOfWeek = listOf<String>("일","월","화","수","목","금","토")

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
    fun getDayOfMonth(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getKoreaLocalTime().dayOfMonth
        }else {
            calendar.get(Calendar.DAY_OF_MONTH)
        }
    }
    fun getDayOfWeek(year : Int,month : Int,day : Int) : String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.of(year,month,day).dayOfWeek.toString().changeKoreanDayOfWeek()
        } else {
            val diffDay = getDiffDay(year,month,day)
            return if (diffDay >= 0) {
                koreanDayOfWeek[(calendar.get(Calendar.DAY_OF_WEEK) - 1 + diffDay) % 7]
            } else {
                koreanDayOfWeek[abs(calendar.get(Calendar.DAY_OF_WEEK) - 1 + (7-(diffDay%7))) % 7]
            }
        }
    }

    private fun String.changeKoreanDayOfWeek(): String =
        when (this) {
            "MONDAY" -> "월"
            "TUESDAY" -> "화"
            "WEDNESDAY" -> "수"
            "THURSDAY" -> "목"
            "FRIDAY" -> "금"
            "SATURDAY" -> "토"
            "SUNDAY" -> "일"
            else -> "error"
        }

    private fun getDiffDay(year : Int,month : Int,day : Int) : Int {
        val todayYear = getYear()
        val todayMonth = getMonth()
        val todayDay = getDayOfMonth()
        val today = ("" + todayYear + todayMonth + todayDay).toInt()
        val selectDay = ("" + year + month + day).toInt()
        return  today - selectDay
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