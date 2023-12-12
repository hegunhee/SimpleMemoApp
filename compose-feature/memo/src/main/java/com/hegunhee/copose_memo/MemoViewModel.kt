package com.hegunhee.copose_memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.util.DateUtil
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.model.TotalPrice
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetMemoTypeListSortedByYearAndMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getAllMemoTypeBySortUseCase : GetMemoTypeListSortedByYearAndMonthUseCase
) : ViewModel() {

    private val yearDate = MutableStateFlow<Int>(com.hegunhee.newsimplememoapp.util.DateUtil.getYear())
    private val monthDate = MutableStateFlow<Int>(com.hegunhee.newsimplememoapp.util.DateUtil.getMonth())

    val uiState: StateFlow<MemoUiState> = yearDate.combine(monthDate) { year, month ->
        val memoList = getAllMemoTypeBySortUseCase(year, month)
        val totalPrice = memoList.filterIsInstance<MemoType.Memo>().getTotalPrice()
        MemoUiState.Success(
            year = year,
            month = month,
            memoTypeList = memoList,
            totalPrice = totalPrice
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = MemoUiState.Loading
    )


    private fun List<MemoType.Memo>.getTotalPrice(): TotalPrice {
        val incomeTotal = this.filter { it.category == "수입" }.sumOf { it.price }
        val expenseTotal = this.filter { it.category == "지출" }.sumOf { it.price }
        return TotalPrice(incomeTotal, expenseTotal)
    }

    fun onPreviousMonthClick() {
        if (monthDate.value <= 1) {
            yearDate.value = yearDate.value - 1
            monthDate.value = 12
        } else {
            monthDate.value = monthDate.value - 1
        }
    }

    fun onNextMonthClick() {
        if (monthDate.value >= 12) {
            yearDate.value = yearDate.value + 1
            monthDate.value = 1
        } else {
            monthDate.value = monthDate.value + 1
        }
    }

    fun onDatePickerMonthClick(year : Int, month : Int) {
        yearDate.value = year
        monthDate.value = month
    }

    fun onDatePickerCurrentMonthClick() {
        yearDate.value = com.hegunhee.newsimplememoapp.util.DateUtil.getYear()
        monthDate.value = com.hegunhee.newsimplememoapp.util.DateUtil.getMonth()
    }
}