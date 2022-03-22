package com.hegunhee.newsimplememoapp.ui.addMemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.min

class AddMemoViewModel : ViewModel() {

    val category = MutableLiveData<String>()

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var day_of_week: String = ""
    val date_Info = MutableLiveData<String>()

    var ampm: String = ""
    var hour: Int = 0
    var minute: Int = 0
    var time_Info = MutableLiveData<String>()

    fun initData() {
        category.value = "지출"
        setDate()
        initTime()

    }


    //Dialog로 데이터를 받아서 사용할때는 LocalDate.of를 사용하자

    fun setDate(date: LocalDate = LocalDate.now()) {

        year = date.year
        month = date.monthValue
        day = date.dayOfMonth
        day_of_week = transferdayofweekbyKorean(date.dayOfWeek.toString())
        date_Info.value = "${year}/${month}/${day} (${day_of_week})"
    }

    private fun initTime() {
        val day = LocalDateTime.now().plusHours(9)
        if (day.hour > 12) {
            ampm = "오후"
            hour = day.hour - 12
            minute = day.minute
        } else {
            ampm = "오전"
            hour = day.hour
            minute = day.minute
        }
        setTimeInfo()
    }

    fun setTimeInfo(){
        time_Info.value = "$ampm ${hour}:${minute}"
    }

    fun setCategoryIncome() {
        category.value = "수입"
    }

    fun setCategoryExpense() {
        category.value = "지출"
    }

    private fun transferdayofweekbyKorean(day_of_week: String): String {
        return when (day_of_week) {
            "MONDAY" -> "월"
            "TUESDAY" -> "화"
            "WEDNESDAY" -> "수"
            "THURSDAY" -> "목"
            "FRIDAY" -> "금"
            "SATURDAY" -> "토"
            "SUNDAY" -> "일"
            else -> "error"
        }
    }
}