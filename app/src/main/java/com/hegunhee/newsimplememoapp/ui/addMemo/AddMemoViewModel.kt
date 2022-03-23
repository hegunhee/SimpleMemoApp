package com.hegunhee.newsimplememoapp.ui.addMemo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.data.entity.isExpenseAttr
import com.hegunhee.newsimplememoapp.data.entity.isIncomeAttr
import com.hegunhee.newsimplememoapp.domain.memoUsecase.AddMemoUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.min

class AddMemoViewModel(
    private val addMemoUseCase : AddMemoUseCase
) : ViewModel() {

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

    var asset = MutableLiveData<String>()
    var attr = MutableLiveData<String>()

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
        if(isExpenseAttr(attr.value?:return)){
            attr.value = ""
        }
    }

    fun setCategoryExpense() {
        category.value = "지출"
        if(isIncomeAttr(attr.value?:return)){
            attr.value = ""
        }
    }

    fun saveData(price : Int, desc : String) = viewModelScope.launch {
        val memo = Memo(category.value!!,year,month,day,day_of_week,ampm,hour,minute,asset.value!!,price,attr.value!!,desc)
        Log.d("TestSaveMemo",memo.toString())
        addMemoUseCase(memo)
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