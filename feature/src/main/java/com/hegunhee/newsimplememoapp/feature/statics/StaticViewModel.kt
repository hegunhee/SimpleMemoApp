package com.hegunhee.newsimplememoapp.feature.statics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemo
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetStaticsDataUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetStaticsMemosUseCase
import com.hegunhee.newsimplememoapp.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaticViewModel @Inject constructor(
    private val getStaticsMemosUseCase: GetStaticsMemosUseCase,
    private val getStaticsDataUseCase: GetStaticsDataUseCase
) : ViewModel(), StaticsActionHandler {

    private val _staticsNavigation : MutableSharedFlow<StaticsNavigation> = MutableSharedFlow()
    val staticsNavigation : SharedFlow<StaticsNavigation> = _staticsNavigation.asSharedFlow()

    private val _incomeExpenseType : MutableStateFlow<IncomeExpenseType> = MutableStateFlow(IncomeExpenseType.INCOME)
    val incomeExpenseType : StateFlow<IncomeExpenseType> = _incomeExpenseType.asStateFlow()

    val yearDate = MutableStateFlow(DateUtil.getYear())
    val monthDate = MutableStateFlow(DateUtil.getMonth())

    val staticsMemo : StateFlow<List<StaticsMemo>> = combine(incomeExpenseType,yearDate,monthDate) { type, year, month ->
        getStaticsMemosUseCase(type,year,month)
            .onSuccess { memos ->
                return@combine memos.staticsMemos
            }.onFailure {

            }
        return@combine emptyList<StaticsMemo>()
    }.stateIn(
        scope = viewModelScope,
        started= SharingStarted.WhileSubscribed(500L),
        initialValue = emptyList()
    )

    override fun onPreviousMonthClick() {
        if (monthDate.value <= 1) {
            yearDate.value = yearDate.value - 1
            monthDate.value = 12
        } else {
            monthDate.value = monthDate.value - 1
        }
    }

    override fun onNextMonthClick() {
        if (monthDate.value >= 12) {
            yearDate.value = yearDate.value + 1
            monthDate.value = 1
        } else {
            monthDate.value = monthDate.value + 1
        }
    }

    override fun onDateSelectClick() {
        viewModelScope.launch {
            _staticsNavigation.emit(StaticsNavigation.SelectDate)
        }
    }

    override fun onStaticsDetailClick(attr : String) {
        viewModelScope.launch {
            _staticsNavigation.emit(StaticsNavigation.Detail(StaticsNavArgs(attr,yearDate.value,monthDate.value)))
        }
    }

    fun setDate(year : Int,month : Int) {
        yearDate.value = year
        monthDate.value = month
    }


    fun onIncomeTabClick() {
        _incomeExpenseType.value = IncomeExpenseType.INCOME
    }

    fun onExpenseTabClick() {
        _incomeExpenseType.value = IncomeExpenseType.EXPENSE
    }

}