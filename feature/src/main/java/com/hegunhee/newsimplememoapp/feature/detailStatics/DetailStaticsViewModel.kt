package com.hegunhee.newsimplememoapp.feature.detailStatics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetMemoListSortedByAttrYearMonthUseCase
import com.hegunhee.newsimplememoapp.feature.common.DateSelectorActionHandler
import com.hegunhee.newsimplememoapp.feature.common.MemoAdapterActionHandler
import com.hegunhee.newsimplememoapp.feature.common.toMoneyFormat
import com.hegunhee.newsimplememoapp.feature.statics.StaticsNavArgs
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailStaticsViewModel @Inject constructor(
    private val getMemoListSortedByAttrYearMonthUseCase: GetMemoListSortedByAttrYearMonthUseCase
) : ViewModel(), DateSelectorActionHandler, MemoAdapterActionHandler {

    val yearDate : MutableStateFlow<Int> = MutableStateFlow(DateUtil.getYear())
    val monthDate : MutableStateFlow<Int> = MutableStateFlow(DateUtil.getMonth())

    private val _attr : MutableStateFlow<String> = MutableStateFlow<String>("")
    val attr : StateFlow<String> = _attr.asStateFlow()

    private val _navigationEvent : MutableSharedFlow<DetailStaticsNavigation> = MutableSharedFlow()
    val navigationEvent : SharedFlow<DetailStaticsNavigation> = _navigationEvent.asSharedFlow()

    val memoList : StateFlow<List<MemoType>> = yearDate.combine(monthDate) { year, month ->
        getMemoListSortedByAttrYearMonthUseCase(attr.value,year,month)
    }.stateIn(
        scope = viewModelScope,
        started= SharingStarted.WhileSubscribed(100L),
        initialValue = emptyList()
    )

    val totalText : StateFlow<String> = memoList.map {
        if(it.isEmpty()) {
            ""
        }else {
            it.filterIsInstance<MemoType.Memo>().sumOf { it.price }.toMoneyFormat() +"원"
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(100L),
        initialValue = ""
    )

    fun initData(staticsData : StaticsNavArgs){
        staticsData.run {
            yearDate.value = year
            monthDate.value = month
            _attr.value = attr
        }
    }

    fun setDate(year : Int,month : Int) {
        yearDate.value = year
        monthDate.value = month
    }

    fun onBackButtonClick(){
        viewModelScope.launch {
            _navigationEvent.emit(DetailStaticsNavigation.Back)
        }
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
            _navigationEvent.emit(DetailStaticsNavigation.DateSelect)
        }
    }

    override fun detailMemo(memoId: Int) {
        viewModelScope.launch {
            _navigationEvent.emit(DetailStaticsNavigation.DetailMemo(memoId))
        }
    }
}