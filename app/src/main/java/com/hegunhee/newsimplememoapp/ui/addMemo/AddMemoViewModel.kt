package com.hegunhee.newsimplememoapp.ui.addMemo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.data.entity.changeKoreanDayOfWeek
import com.hegunhee.newsimplememoapp.data.entity.isExpenseAttr
import com.hegunhee.newsimplememoapp.data.entity.isIncomeAttr
import com.hegunhee.newsimplememoapp.domain.memoUsecase.InsertMemoUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class AddMemoViewModel(
    private val addMemoUseCase : InsertMemoUseCase
) : ViewModel() {

    val category = MutableLiveData<String>()

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var dayOfWeek: String = ""
    val dateInfo = MutableLiveData<String>()

    var ampm: String = ""
    var hour: Int = 0
    var minute: Int = 0
    var timeInfo = MutableLiveData<String>()

    var asset = MutableLiveData<String>()
    var attr = MutableLiveData<String>()

    val memoState = MutableLiveData<AddMemoState>(AddMemoState.Uninitialized)


    fun initData() {
        category.value = "지출"
        setDate()
        initTime()
    }
    private fun initTime() {
        val day = LocalDateTime.now().plusHours(9)
        if (day.hour > 12) {
            ampm = "오후"
            hour = day.hour - 12
            minute = day.minute
        } else {
            ampm = "오전"
            hour = day.hour
            minute = day.minute
        }
        setTimeInfo()
    }

    fun setDate(date: LocalDate = LocalDate.now()) {
        year = date.year
        month = date.monthValue
        day = date.dayOfMonth
        dayOfWeek = changeKoreanDayOfWeek(date.dayOfWeek.toString())
        dateInfo.value = "${year}/${month}/${day} (${dayOfWeek})"
    }


    fun setTimeInfo(){
        timeInfo.value = "$ampm ${hour}:${minute}"
    }

    fun setCategoryIncome() {
        category.value = "수입"
        if(isExpenseAttr(attr.value?:return)){
            attr.value = ""
        }
    }

    fun setCategoryExpense() {
        category.value = "지출"
        if(isIncomeAttr(attr.value?:return)){
            attr.value = ""
        }
    }


    fun saveData(price : Int, desc : String) = viewModelScope.launch {
        val memo = Memo(category.value!!,year,month,day,dayOfWeek,ampm,hour,minute,attr.value!!,price,asset.value!!,desc)
        Log.d("TestSaveMemo",memo.toString())
        addMemoUseCase(memo)
    }

    fun back(){
        memoState.postValue(AddMemoState.Back)
    }
    fun clickDate(){
        memoState.postValue(AddMemoState.SetDate)
    }
    fun clickTime(){
        memoState.postValue(AddMemoState.SetTime)
    }
    fun clickAsset(){
        memoState.postValue(AddMemoState.SetAsset)
    }
    fun clickAttr() {
        memoState.postValue(AddMemoState.SetAttr)
    }

    fun clickSave(){
        memoState.postValue(AddMemoState.Save)
    }


}