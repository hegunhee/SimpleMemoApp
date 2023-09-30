package com.hegunhee.newsimplememoapp.feature.statics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.GetStaticsDataUseCase
import com.hegunhee.newsimplememoapp.feature.common.DateSelectorActionHandler
import com.hegunhee.newsimplememoapp.feature.memo.DateNavigation
import com.hegunhee.newsimplememoapp.feature.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaticViewModel @Inject constructor(
    private val getStaticsDataUseCase: GetStaticsDataUseCase
) : ViewModel(), DateSelectorActionHandler {

    private var _staticsData = MutableLiveData<StaticsState>(StaticsState.Uninitialized)
    val staticsData = _staticsData
    val category = MutableLiveData<String>()

    private val _dateNavigation : MutableSharedFlow<DateNavigation> = MutableSharedFlow()
    val dateNavigation : SharedFlow<DateNavigation> = _dateNavigation.asSharedFlow()


    val yearDate = MutableStateFlow<Int>(DateUtil.getYear())
    val monthDate = MutableStateFlow<Int>(DateUtil.getMonth())

    val recyclerViewVisible = MutableLiveData<Boolean>(false)
    val totalText = MutableLiveData<String>()



    fun initDate() {
        val year = DateUtil.getYear()
        val month = DateUtil.getMonth()
        yearDate.value = year
        monthDate.value = month
        initCategory()
        setData(year,month)
    }

    private fun initCategory() {
        category.value = "지출"
    }

    override fun onPreviousMonthClick() {
        if (monthDate.value <= 1) {
            yearDate.value = yearDate.value - 1
            monthDate.value = 12
        } else {
            monthDate.value = monthDate.value - 1
        }
        setData()
    }

    override fun onNextMonthClick() {
        if (monthDate.value >= 12) {
            yearDate.value = yearDate.value + 1
            monthDate.value = 1
        } else {
            monthDate.value = monthDate.value + 1
        }
        setData()
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


    fun setIncome() {
        if (category.value == "지출") {
            category.value = "수입"
            setData()
        }

    }

    fun setExpense() {
        if (category.value == "수입") {
            category.value = "지출"
            setData()
        }
    }

    fun setData(year: Int = yearDate.value, month: Int = monthDate.value) = viewModelScope.launch {
        val category = category.value!!
//        getStaticsDataUseCase(category,year,month).run {
//            if(this.isNullOrEmpty()){
//                recyclerViewVisible.value = false
//                _staticsData.postValue(StaticsState.EmptyOrNull)
//            }else{
//                recyclerViewVisible.value = true
//                totalText.value = "합계 : ${this.sumOf { it.price }} 원"
//                _staticsData.postValue(StaticsState.Success(this))
//            }
//        }
    }
}