package com.hegunhee.statics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.compose_feature.util.DateUtil
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetStaticsDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StaticsViewModel @Inject constructor(
    private val getStaticsDataUseCase: GetStaticsDataUseCase
) : ViewModel() {

    private val yearDate = MutableStateFlow(DateUtil.getYear())
    private val monthDate = MutableStateFlow(DateUtil.getMonth())
    private val memoCategory = MutableStateFlow("수입")


    val uiState : StateFlow<StaticsUiState> = combine(flow = yearDate,flow2 = monthDate,flow3 = memoCategory,transform = { year, month,category ->
        val staticsList = getStaticsDataUseCase(year,month).filter { it.category == category }
        StaticsUiState.Success(
            year,
            month,
            category,
            staticsList
        )
    }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = StaticsUiState.Loading
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

    fun onDatePickerMonthClick(year : Int, month : Int) {
        yearDate.value = year
        monthDate.value = month
    }

    fun onDatePickerCurrentMonthClick() {
        yearDate.value = DateUtil.getYear()
        monthDate.value = DateUtil.getMonth()
    }

    fun setCategory(category : String) {
        memoCategory.value = category
    }

}