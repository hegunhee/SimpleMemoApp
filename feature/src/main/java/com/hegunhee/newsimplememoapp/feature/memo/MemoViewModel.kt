package com.hegunhee.newsimplememoapp.feature.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetMemoTypeListSortedByYearAndMonthUseCase
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.feature.common.DateSelectorActionHandler
import com.hegunhee.newsimplememoapp.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getAllMemoTypeBySortUseCase : GetMemoTypeListSortedByYearAndMonthUseCase
) : ViewModel(), MemoActionHandler, DateSelectorActionHandler {

    private val _memoNavigation : MutableSharedFlow<MemoNavigation> = MutableSharedFlow<MemoNavigation>()
    val memoNavigation : SharedFlow<MemoNavigation> = _memoNavigation.asSharedFlow()

    private val _dateNavigation : MutableSharedFlow<DateNavigation> = MutableSharedFlow()
    val dateNavigation : SharedFlow<DateNavigation> = _dateNavigation.asSharedFlow()

    val yearDate = MutableStateFlow<Int>(DateUtil.getYear())
    val monthDate = MutableStateFlow<Int>(DateUtil.getMonth())

    val memoList : StateFlow<List<MemoType>> = yearDate.combine(monthDate) { year, month ->
        val memoList = getAllMemoTypeBySortUseCase(year,month)
        setPrices(memoList.filterIsInstance<MemoType.Memo>())
        memoList
    }.stateIn(
        scope =  viewModelScope,
        started = SharingStarted.WhileSubscribed(100L),
        initialValue = emptyList()
    )

    val incomeValue = MutableStateFlow<Int>(0)
    val expenseValue = MutableStateFlow<Int>(0)
    val totalValue = MutableStateFlow<Int>(0)

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

    fun setDate(year : Int,month : Int) {
        yearDate.value = year
        monthDate.value = month
    }

    private fun setPrices(memoList : List<MemoType.Memo>) {
        incomeValue.value = memoList.filter { it.category == "수입" }.sumOf { it.price }
        expenseValue.value = memoList.filter { it.category != "수입" }.sumOf { it.price }
        totalValue.value = incomeValue.value - expenseValue.value
    }
    override fun addMemo() {
        viewModelScope.launch {
            _memoNavigation.emit(MemoNavigation.AddMemo)
        }
    }

    override fun detailMemo(memoId: Int) {
        viewModelScope.launch {
            _memoNavigation.emit(MemoNavigation.DetailMemo(memoId))
        }
    }

    override fun onDateSelectClick() {
        viewModelScope.launch {
            _dateNavigation.emit(DateNavigation.SelectDate)
        }
    }
}