package com.hegunhee.statics.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.util.DateUtil
import com.hegunhee.newsimplememoapp.core.ui.toMoneyFormat
import com.hegunhee.newsimplememoapp.domain.model.memo.toMemoTypes
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetMemosByAttrUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailStaticsViewModel @Inject constructor(
    private val getMemosByAttrYearMonthUseCase: GetMemosByAttrUseCase
) : ViewModel() {

    private val yearDate = MutableStateFlow(DateUtil.getYear())
    private val monthDate = MutableStateFlow(DateUtil.getMonth())
    private val attr = MutableStateFlow("")

    val uiState : StateFlow<DetailStaticsUiState> = combine(yearDate,monthDate,attr) { year, month, attr ->
        getMemosByAttrYearMonthUseCase(attr,year,month)
            .onSuccess { memos ->
                return@combine DetailStaticsUiState.Success(
                    year,
                    month,
                    attr,
                    memos.memos.toMemoTypes(),
                    memos.price.intValueExact().toMoneyFormat()
                )
            }.onFailure {

            }
        DetailStaticsUiState.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = DetailStaticsUiState.Loading
    )

    fun initAttr(attrName : String) {
        attr.value = attrName
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
        yearDate.value = DateUtil.getYear()
        monthDate.value = DateUtil.getMonth()
    }

}