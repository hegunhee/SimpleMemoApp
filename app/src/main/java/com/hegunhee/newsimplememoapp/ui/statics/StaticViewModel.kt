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
        val data = getStaticsDataUseCase(category,year,month)
        if(data.isEmpty()){
            recyclerViewVisible.value = false
            _staticsData.postValue(StaticsState.EmptyOrNull)
            Log.d("testData","data is empty")
        }else{
            recyclerViewVisible.value = true
            totalText.value = "합계 : ${data.sumOf { it.price }} 원"
            _staticsData.postValue(StaticsState.Success(data))
            Log.d("testData",data.toString())
        }
    }
}