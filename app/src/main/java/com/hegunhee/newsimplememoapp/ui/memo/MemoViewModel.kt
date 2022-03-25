package com.hegunhee.newsimplememoapp.ui.memo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetMemoSortedByYearAndMonthUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.exp

class MemoViewModel(
    private val getAllDataBySort: GetMemoSortedByYearAndMonthUseCase
) : ViewModel() {

    private var _memoList = MutableLiveData<MemoState>(MemoState.Uninitialized)
    val memoList = _memoList

    val yearDate = MutableLiveData<Int>()
    val monthDate = MutableLiveData<Int>()

    val incomeValue = MutableLiveData<Int>()
    val expenseValue = MutableLiveData<Int>()
    val totalValue = MutableLiveData<Int>()




    fun initDate() {
        val date = LocalDate.now()
        yearDate.value = date.year
        monthDate.value = date.monthValue
        setData(date.year, date.monthValue)
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
        viewModelScope.launch {
            val data = getAllDataBySort(year,month)
            if(data.isNullOrEmpty()){
                _memoList.postValue(MemoState.EmptyOrNull)
                incomeValue.value = 0
                expenseValue.value = 0
                totalValue.value = 0
            }else{
                _memoList.postValue(MemoState.Success(data))
                incomeValue.value = data.filter { it.category == "수입" }.map { it.price }.sum()
                expenseValue.value = data.filter { it.category != "수입" }.map { it.price }.sum()
                totalValue.value = incomeValue.value!! - expenseValue.value!!
            }
        }

    }

    fun refreshData(){
        Log.d("Refresh","Refresh")
        setData(yearDate.value!!,monthDate.value!!)
    }


}