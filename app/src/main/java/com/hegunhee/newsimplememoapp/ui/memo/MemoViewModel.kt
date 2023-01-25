package com.hegunhee.newsimplememoapp.ui.memo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetMemoSortedByYearAndMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getAllDataBySort: GetMemoSortedByYearAndMonthUseCase
) : ViewModel() {

    val yearDate = MutableLiveData<Int>()
    val monthDate = MutableLiveData<Int>()

    val incomeValue = MutableLiveData<Int>()
    val expenseValue = MutableLiveData<Int>()
    val totalValue = MutableLiveData<Int>()

    fun initDate() = LocalDate.now().run {
        yearDate.value = year
        monthDate.value = monthValue
        setData(year, monthValue)
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

    private val _memoList : MutableStateFlow<List<Memo>> = MutableStateFlow(emptyList())
    val memoList : StateFlow<List<Memo>> = _memoList.asStateFlow()

    private fun setData(year: Int, month: Int) {
        viewModelScope.launch {
            getAllDataBySort(year, month).let { data->
                _memoList.emit(data)
                incomeValue.value = data.filter { it.category == "수입" }.map { it.price }.sum()
                expenseValue.value = data.filter { it.category != "수입" }.map { it.price }.sum()
                totalValue.value = incomeValue.value!! - expenseValue.value!!
            }
        }

    }


}