package com.hegunhee.newsimplememoapp.ui.detailStatics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hegunhee.newsimplememoapp.ui.statics.StaticsData

class DetaiStaticsViewModel() : ViewModel() {

    val yearDate = MutableLiveData<Int>(1)
    val monthDate = MutableLiveData<Int>(1)

    val attrData = MutableLiveData<String>("Init")

    val backButton = MutableLiveData<BackButtonState>(BackButtonState.Uninitialized)

    val recyclerViewVisible = MutableLiveData<Boolean>(false)



    fun initData(staticsData : StaticsData){
        staticsData.run {
            yearDate.value = year
            monthDate.value = month
            attrData.value = attr
        }
    }

    fun clickLeft() {
        if (monthDate.value!! <= 1) {
            yearDate.value = yearDate.value!! - 1
            monthDate.value = 12
        } else {
            monthDate.value = monthDate.value!! - 1
        }
        setData(yearDate.value!!, monthDate.value!!)
    }

    fun clickRight() {
        if (monthDate.value!! >= 12) {
            yearDate.value = yearDate.value!! + 1
            monthDate.value = 1
        } else {
            monthDate.value = monthDate.value!! + 1
        }
        setData(yearDate.value!!, monthDate.value!!)
    }

    fun setData(year : Int, month : Int){

    }

    fun back(){
        backButton.postValue(BackButtonState.Back)
    }


}