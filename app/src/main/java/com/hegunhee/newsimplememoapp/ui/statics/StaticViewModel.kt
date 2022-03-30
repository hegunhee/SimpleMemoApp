package com.hegunhee.newsimplememoapp.ui.statics

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class StaticViewModel() : ViewModel() {

    val category = MutableLiveData<String>()
    val yearDate = MutableLiveData<Int>()
    val monthDate = MutableLiveData<Int>()


    fun initDate() {
        val date = LocalDate.now()
        yearDate.value = date.year
        monthDate.value = date.monthValue
        initCategory()
        setData(date.year, date.monthValue)
    }

    private fun initCategory() {
        category.value = "지출"
    }

    fun clickLeft() {
        if (monthDate.value!! <= 1) {
            yearDate.value = yearDate.value!! - 1
            monthDate.value = 12
        } else {
            monthDate.value = monthDate.value!! - 1
        }
        setData(yearDate.value!!, monthDate.value!!)
    }

    fun clickRight() {
        if (monthDate.value!! >= 12) {
            yearDate.value = yearDate.value!! + 1
            monthDate.value = 1
        } else {
            monthDate.value = monthDate.value!! + 1
        }
        setData(yearDate.value!!, monthDate.value!!)
    }

    private fun setData(year: Int, month: Int) {
    }

    fun setIncome() {
        if (category.value == "지출") {
            category.value = "수입"
            setData(yearDate.value!!,monthDate.value!!)
        }

    }

    fun setExpense() {
        if (category.value == "수입") {
            category.value = "지출"
            setData(yearDate.value!!,monthDate.value!!)
        }
    }
}