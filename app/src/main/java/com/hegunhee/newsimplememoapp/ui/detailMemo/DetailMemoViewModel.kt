package com.hegunhee.newsimplememoapp.ui.detailMemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hegunhee.newsimplememoapp.data.entity.Memo

class DetailMemoViewModel : ViewModel() {

    private lateinit var memo : Memo

    val category = MutableLiveData<String>()

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var day_of_week: String = ""
    val date_Info = MutableLiveData<String>()

    var ampm: String = ""
    var hour: Int = 0
    var minute: Int = 0
    var time_Info = MutableLiveData<String>()

    val asset = MutableLiveData<String>()
    val attr = MutableLiveData<String>()

    val price = MutableLiveData<String>()
    val desc = MutableLiveData<String>()


    fun initViewModel(memo : Memo){
        this.memo = memo
        initData()
    }

    private fun initData(){
        initDay()
        initTime()
        asset.value = memo.asset
        attr.value = memo.attr
        price.value = memo.price.toString()
        desc.value = memo.description
    }
    private fun initDay(){
        year = memo.year
        month = memo.month
        day = memo.day
        day_of_week = memo.dayOfWeek
        date_Info.value = "${year}/${month}/${day} (${day_of_week}) "
    }
    private fun initTime(){
        ampm = memo.amPm
        hour = memo.hour
        minute = memo.minute
        time_Info.value = "$ampm ${hour}:${minute}"
    }



}