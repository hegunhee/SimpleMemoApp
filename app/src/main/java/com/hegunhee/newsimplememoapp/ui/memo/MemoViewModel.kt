package com.hegunhee.newsimplememoapp.ui.memo

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetMemoSortedByYearAndMonthUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class MemoViewModel(
    private val getAllDataBySort : GetMemoSortedByYearAndMonthUseCase
) : ViewModel() {

    var memoList = ObservableArrayList<Memo>()

    val year_date = MutableLiveData<Int>()
    val month_date = MutableLiveData<Int>()

    val income_value = MutableLiveData<Int>()
    val expense_value = MutableLiveData<Int>()
    val total_value = MutableLiveData<Int>()

    fun init_date(){
        val date = LocalDate.now()
        year_date.value = date.year
        month_date.value = date.monthValue
        setData(date.year,date.monthValue)
    }

    fun click_left(){
        if(month_date.value!! <= 1){
            year_date.value = year_date.value!! -1
            month_date.value = 12
        }else{
            month_date.value = month_date.value!! -1
        }
        setData(year_date.value!!,month_date.value!!)
    }

    fun click_right(){
        if(month_date.value!! >= 12){
            year_date.value = year_date.value!! +1
            month_date.value = 1
        }else{
            month_date.value = month_date.value!! +1
        }
        setData(year_date.value!!,month_date.value!!)
    }

    fun setData(year : Int,month : Int){
        viewModelScope.launch {
            val data = getAllDataBySort(year,month)
            memoList = data.toCollection(ObservableArrayList())
            income_value.value = memoList.filter { it.category=="수입" }.map { it.price }.sum()
            expense_value.value = memoList.filter { it.category=="지출" }.map { it.price }.sum()
            total_value.value = income_value.value!! - expense_value.value!!
            Log.d("TestData","${year} ${month}")
            Log.d("TestData",memoList.toString())

        }

    }


}