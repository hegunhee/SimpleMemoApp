package com.hegunhee.newsimplememoapp.ui.detailMemo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.data.entity.isExpenseAttr
import com.hegunhee.newsimplememoapp.data.entity.isIncomeAttr
import com.hegunhee.newsimplememoapp.domain.memoUsecase.AddMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.DeleteMemoUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class DetailMemoViewModel(
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val addMemoUseCase: AddMemoUseCase
) : ViewModel() {

    private lateinit var memo: Memo

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

    val asset = MutableLiveData<String>()
    val attr = MutableLiveData<String>()

    val price = MutableLiveData<String>()
    val desc = MutableLiveData<String>()


    fun initViewModel(memo: Memo) {
        this.memo = memo
        initData()
    }

    private fun initData() {
        initDay()
        initTime()
        category.value = memo.category
        asset.value = memo.asset
        attr.value = memo.attr
        price.value = memo.price.toString()
        desc.value = memo.description
    }

    private fun initDay() {
        year = memo.year
        month = memo.month
        day = memo.day
        day_of_week = memo.dayOfWeek
        date_Info.value = "${year}/${month}/${day} (${day_of_week}) "
    }

    private fun initTime() {
        ampm = memo.amPm
        hour = memo.hour
        minute = memo.minute
        time_Info.value = "$ampm ${hour}:${minute}"
    }

    fun setDate(date: LocalDate = LocalDate.now()) {

        year = date.year
        month = date.monthValue
        day = date.dayOfMonth
        day_of_week = transferdayofweekbyKorean(date.dayOfWeek.toString())
        date_Info.value = "${year}/${month}/${day} (${day_of_week})"
    }

    fun setTimeInfo() {
        time_Info.value = "$ampm ${hour}:${minute}"
    }

    fun setCategoryIncome() {
        category.value = "수입"
        if (isExpenseAttr(attr.value ?: return)) {
            attr.value = ""
        }
    }

    fun setCategoryExpense() {
        category.value = "지출"
        if (isIncomeAttr(attr.value ?: return)) {
            attr.value = ""
        }
    }

    fun saveData() = viewModelScope.launch {
        val memo = Memo(
            category.value!!,
            year,
            month,
            day,
            day_of_week,
            ampm,
            hour,
            minute,
            attr.value!!,
            price.value.toString().toInt(),
            asset.value!!,
            desc.value!!,
            memo.id
        )
        Log.d("TestSaveMemo", memo.toString())
        addMemoUseCase(memo)
    }


    fun removeMemo() = viewModelScope.launch {
        deleteMemoUseCase.invoke(memo)

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