package com.hegunhee.newsimplememoapp.ui.statics

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.expenseAttr
import com.hegunhee.newsimplememoapp.data.entity.incomeAttr
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetMemoSortedByYearAndMonthUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetStaticsDataUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class StaticViewModel(
    private val getStaticsDataUseCase: GetStaticsDataUseCase
) : ViewModel() {

    private var _staticsData = MutableLiveData<StaticsState>(StaticsState.Uninitialized)
    val staticsData = _staticsData
    val category = MutableLiveData<String>()
    val yearDate = MutableLiveData<Int>()
    val monthDate = MutableLiveData<Int>()

    val recyclerViewVisible = MutableLiveData<Boolean>(false)
    val totalText = MutableLiveData<String>()


    fun initDate() {
        LocalDate.now()?.run{
            yearDate.value = year
            monthDate.value = monthValue
            initCategory()
            setData(year,monthValue)
        }
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


    fun setIncome() {
        if (category.value == "지출") {
            category.value = "수입"
            setData(yearDate.value!!, monthDate.value!!)
        }

    }

    fun setExpense() {
        if (category.value == "수입") {
            category.value = "지출"
            setData(yearDate.value!!, monthDate.value!!)
        }
    }

    private fun setData(year: Int, month: Int) = viewModelScope.launch {
        val category = category.value!!
        getStaticsDataUseCase(category,year,month).run {
            if(this.isNullOrEmpty()){
                recyclerViewVisible.value = false
                _staticsData.postValue(StaticsState.EmptyOrNull)
            }else{
                recyclerViewVisible.value = true
                totalText.value = "합계 : ${this.sumOf { it.price }} 원"
                _staticsData.postValue(StaticsState.Success(this))
            }
        }
    }
}