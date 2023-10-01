package com.hegunhee.newsimplememoapp.feature.statics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.StaticsData
import com.hegunhee.newsimplememoapp.domain.usecase.GetStaticsDataUseCase
import com.hegunhee.newsimplememoapp.feature.common.DateSelectorActionHandler
import com.hegunhee.newsimplememoapp.feature.common.MemoCategory
import com.hegunhee.newsimplememoapp.feature.memo.DateNavigation
import com.hegunhee.newsimplememoapp.feature.util.DateUtil
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
    private val getStaticsDataUseCase: GetStaticsDataUseCase
) : ViewModel(), DateSelectorActionHandler {

    private val _dateNavigation : MutableSharedFlow<DateNavigation> = MutableSharedFlow()
    val dateNavigation : SharedFlow<DateNavigation> = _dateNavigation.asSharedFlow()

    private val _categoryType : MutableStateFlow<MemoCategory> = MutableStateFlow(MemoCategory.Income)
    val categoryType : StateFlow<MemoCategory> = _categoryType.asStateFlow()

    val yearDate = MutableStateFlow<Int>(DateUtil.getYear())
    val monthDate = MutableStateFlow<Int>(DateUtil.getMonth())

    private val staticsData : StateFlow<List<StaticsData>> = yearDate.combine(monthDate) { year, month ->
        getStaticsDataUseCase(year,month)
    }.stateIn(
        scope = viewModelScope,
        started= SharingStarted.WhileSubscribed(500L),
        initialValue = emptyList()
    )

    val filteredStaticsData : StateFlow<List<StaticsData>> = staticsData.combine(categoryType) { staticsList, category ->
        when(category) {
            MemoCategory.Expenses -> staticsList.filter { it.category == MemoCategory.Expenses.text }
            MemoCategory.Income -> staticsList.filter { it.category == MemoCategory.Income.text }
        }
    }.stateIn(
        scope = viewModelScope,
        started= SharingStarted.WhileSubscribed(500L),
        initialValue = emptyList()
    )

    fun initDate() {
        _categoryType.value = MemoCategory.Income
        yearDate.value = DateUtil.getYear()
        monthDate.value = DateUtil.getMonth()
    }

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
            _dateNavigation.emit(DateNavigation.SelectDate)
        }
    }

    fun setDate(year : Int,month : Int) {
        yearDate.value = year
        monthDate.value = month
    }


    fun onIncomeTabClick() {
        _categoryType.value = MemoCategory.Income
    }

    fun onExpenseTabClick() {
        _categoryType.value = MemoCategory.Expenses
    }

}