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

    val year_date = MutableLiveData<Int>()
    val month_date = MutableLiveData<Int>()

    val income_value = MutableLiveData<Int>()
    val expense_value = MutableLiveData<Int>()
    val total_value = MutableLiveData<Int>()




    fun init_date() {
        val date = LocalDate.now()
        year_date.value = date.year
        month_date.value = date.monthValue
        setData(date.year, date.monthValue)
    }

    fun click_left() {
        if (month_date.value!! <= 1) {
            year_date.value = year_date.value!! - 1
            month_date.value = 12
        } else {
            month_date.value = month_date.value!! - 1
        }
        setData(year_date.value!!, month_date.value!!)
    }

    fun click_right() {
        if (month_date.value!! >= 12) {
            year_date.value = year_date.value!! + 1
            month_date.value = 1
        } else {
            month_date.value = month_date.value!! + 1
        }
        setData(year_date.value!!, month_date.value!!)
    }


    fun setData(year: Int, month: Int) {
        viewModelScope.launch {
            val data = getAllDataBySort(year,month)
            if(data.isNullOrEmpty()){
                _memoList.postValue(MemoState.EmptyOrNull)
                income_value.value = 0
                expense_value.value = 0
                total_value.value = 0
            }else{
                _memoList.postValue(MemoState.Success(data))
                income_value.value = data.filter { it.category == "수입" }.map { it.price }.sum()
                expense_value.value = data.filter { it.category != "수입" }.map { it.price }.sum()
                total_value.value = income_value.value!! - expense_value.value!!
            }
        }

    }

    fun refreshData(){
        Log.d("Refresh","Refresh")
        setData(year_date.value!!,month_date.value!!)
    }


}