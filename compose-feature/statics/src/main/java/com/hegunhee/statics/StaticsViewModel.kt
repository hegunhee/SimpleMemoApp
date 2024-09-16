package com.hegunhee.statics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.util.DateUtil
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetStaticsMemosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StaticsViewModel @Inject constructor(
    private val getStaticsMemosUseCase: GetStaticsMemosUseCase,
) : ViewModel() {

    private val _incomeExpenseType: MutableStateFlow<IncomeExpenseType> = MutableStateFlow(
        IncomeExpenseType.INCOME
    )
    val incomeExpenseType: StateFlow<IncomeExpenseType> = _incomeExpenseType.asStateFlow()

    val yearDate = MutableStateFlow(DateUtil.getYear())
    val monthDate = MutableStateFlow(DateUtil.getMonth())


    val uiState: StateFlow<StaticsUiState> = combine(yearDate, monthDate, incomeExpenseType) { year, month, incomeExpenseType ->
        getStaticsMemosUseCase(incomeExpenseType, year, month)
            .onSuccess {
                return@combine StaticsUiState.Success(
                    it.year,
                    it.month,
                    it.type,
                    it.staticsMemos
                )
            }.onFailure {

            }
        return@combine StaticsUiState.Loading
    }.stateIn(
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

    fun onDatePickerMonthClick(year: Int, month: Int) {
        yearDate.value = year
        monthDate.value = month
    }

    fun onDatePickerCurrentMonthClick() {
        yearDate.value = DateUtil.getYear()
        monthDate.value = DateUtil.getMonth()
    }

    fun setIncomeExpenseType(type: IncomeExpenseType) {
        _incomeExpenseType.value = type
    }

}