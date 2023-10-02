package com.hegunhee.newsimplememoapp.feature.detailStatics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.GetMemoListSortedByAttrYearMonthUseCase
import com.hegunhee.newsimplememoapp.feature.common.DateSelectorActionHandler
import com.hegunhee.newsimplememoapp.feature.statics.StaticsNavArgs
import com.hegunhee.newsimplememoapp.feature.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetaiStaticsViewModel @Inject constructor(
    private val getMemoListSortedByAttrYearMonthUseCase: GetMemoListSortedByAttrYearMonthUseCase
) : ViewModel(), DateSelectorActionHandler {

    val yearDate : MutableStateFlow<Int> = MutableStateFlow(DateUtil.getYear())
    val monthDate : MutableStateFlow<Int> = MutableStateFlow(DateUtil.getMonth())

    private val _attr : MutableStateFlow<String> = MutableStateFlow<String>("")
    val attr : StateFlow<String> = _attr.asStateFlow()

    val recyclerViewVisible = MutableLiveData<Boolean>(false)

    val totalText = MutableLiveData<String>()

    private val _navigationEvent : MutableSharedFlow<DetailStaticsNavigation> = MutableSharedFlow()
    val navigationEvent : SharedFlow<DetailStaticsNavigation> = _navigationEvent.asSharedFlow()

    fun initData(staticsData : StaticsNavArgs){
        staticsData.run {
            yearDate.value = year
            monthDate.value = month
            _attr.value = attr
        }
        setData()
    }

    fun setDate(year : Int,month : Int) {
        yearDate.value = year
        monthDate.value = month
    }

    fun setData(year : Int = yearDate.value, month : Int = monthDate.value){
        viewModelScope.launch {
//            getMemoListSortedByAttrYearMonthUseCase(attrData.value!!,year,month).run {
//                if(this.isNullOrEmpty()){
//                    recyclerViewVisible.postValue(false)
//                    detailStaticsState.postValue(DetailStaticsState.NullOrEmpty)
//                }else{
//                    recyclerViewVisible.postValue(true)
//                    detailStaticsState.postValue(DetailStaticsState.Success(this))
//                    val sum = this.sumOf { it.price }
//                    totalText.postValue("이번 달 ${attrData.value!!}는 ${sum}원 입니다." )
//                }
//            }

        }
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
}