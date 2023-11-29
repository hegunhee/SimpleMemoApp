package com.hegunhee.compose_feature.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.hegunhee.newsimplememoapp.domain.model.DateInfo
import com.hegunhee.newsimplememoapp.domain.model.TimeInfo
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

object DateUtil {

    private val calendar = Calendar.getInstance().apply {
        time = Date()
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
            calendar.get(Calendar.MONTH) + 1
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
            val diffCalendar = Calendar.getInstance().apply {
                set(year,month-1,day)
            }
            return koreanDayOfWeek[diffCalendar.get(Calendar.DAY_OF_WEEK)]
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

    fun getTodayDate() : DateInfo {
        val year = getYear()
        val month = getMonth()
        val dayOfMonth = getDayOfMonth()
        val dayOfWeek = getDayOfWeek(year,month,dayOfMonth)
        return DateInfo(year,month,dayOfMonth,dayOfWeek)
    }

    fun getTodayTime() : TimeInfo {
        val hour = if(getHour() > 12) {
            getHour() - 12
        }else {
            getHour()
        }
        val minute = getMinute()
        val ampm = if(getHour() <= 12) {
            "오전"
        }else {
            "오후"
        }
        return TimeInfo(hour,minute,ampm)
    }
}