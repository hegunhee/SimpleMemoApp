package com.hegunhee.newsimplememoapp.feature.detailStatics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.GetMemoListSortedByAttrYearMonthUseCase
import com.hegunhee.newsimplememoapp.domain.model.StaticsData
import com.hegunhee.newsimplememoapp.feature.statics.StaticsNavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetaiStaticsViewModel @Inject constructor(
    private val getMemoListSortedByAttrYearMonthUseCase: GetMemoListSortedByAttrYearMonthUseCase
) : ViewModel() {

    val yearDate = MutableLiveData<Int>(1)
    val monthDate = MutableLiveData<Int>(1)

    val attrData = MutableLiveData<String>("Init")

    val backButton = MutableLiveData<BackButtonState>(BackButtonState.Uninitialized)

    val recyclerViewVisible = MutableLiveData<Boolean>(false)

    val detailStaticsState = MutableLiveData<DetailStaticsState>(DetailStaticsState.Uninitialized)

    val totalText = MutableLiveData<String>()



    fun initData(staticsData : StaticsNavArgs){
        staticsData.run {
            yearDate.value = year
            monthDate.value = month
            attrData.value = attr
        }
        setData()
    }

    fun clickLeft() {
        if (monthDate.value!! <= 1) {
            yearDate.value = yearDate.value!! - 1
            monthDate.value = 12
        } else {
            monthDate.value = monthDate.value!! - 1
        }
        setData()
    }

    fun clickRight() {
        if (monthDate.value!! >= 12) {
            yearDate.value = yearDate.value!! + 1
            monthDate.value = 1
        } else {
            monthDate.value = monthDate.value!! + 1
        }
        setData()
    }

    fun setData(year : Int = yearDate.value!!, month : Int = monthDate.value!!){
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

    fun back(){
        backButton.postValue(BackButtonState.Back)
    }


}