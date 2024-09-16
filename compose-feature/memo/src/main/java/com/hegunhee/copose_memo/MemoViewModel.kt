package com.hegunhee.copose_memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.util.DateUtil
import com.hegunhee.newsimplememoapp.domain.model.memo.toMemoTypes
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetMemosSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getMemosSummaryUseCase: GetMemosSummaryUseCase
) : ViewModel() {

    private val yearDate = MutableStateFlow(DateUtil.getYear())
    private val monthDate = MutableStateFlow(DateUtil.getMonth())

    val uiState: StateFlow<MemoUiState> = yearDate.combine(monthDate) { year, month ->
        val summary = getMemosSummaryUseCase(year, month).getOrElse {
            val message = it.message ?: ""
            return@combine MemoUiState.Error(message)
        }
        MemoUiState.Success(
            year = year,
            month = month,
            memoTypeList = summary.memos.toMemoTypes(),
            totalSum = summary.totalSum
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = MemoUiState.Loading
    )

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

    fun onDatePickerMonthClick(year: Int, month: Int) {
        yearDate.value = year
        monthDate.value = month
    }

    fun onDatePickerCurrentMonthClick() {
        yearDate.value = DateUtil.getYear()
        monthDate.value = DateUtil.getMonth()
    }
}