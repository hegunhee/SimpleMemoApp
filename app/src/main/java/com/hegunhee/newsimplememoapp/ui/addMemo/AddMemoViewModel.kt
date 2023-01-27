package com.hegunhee.newsimplememoapp.ui.addMemo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.data.entity.changeKoreanDayOfWeek
import com.hegunhee.newsimplememoapp.data.entity.isExpenseAttr
import com.hegunhee.newsimplememoapp.data.entity.isIncomeAttr
import com.hegunhee.newsimplememoapp.domain.memoUsecase.InsertMemoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AddMemoViewModel @Inject constructor(
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

    private val _memoState : MutableSharedFlow<AddMemoState> = MutableSharedFlow<AddMemoState>()
    val memoState : SharedFlow<AddMemoState> = _memoState.asSharedFlow()

    fun initData() {
        category.value = "지출"
        setDate()
        initTime()
    }
    private fun initTime() {
        val day = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
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
        val memoEntity = MemoEntity(category.value!!,year,month,day,dayOfWeek,ampm,hour,minute,attr.value!!,price,asset.value!!,desc)
        Log.d("TestSaveMemo",memoEntity.toString())
        addMemoUseCase(memoEntity)
    }

    fun back()= viewModelScope.launch{
        _memoState.emit(AddMemoState.Back)
    }
    fun clickDate() = viewModelScope.launch{
        _memoState.emit(AddMemoState.SetDate)
    }
    fun clickTime() = viewModelScope.launch{
        _memoState.emit(AddMemoState.SetTime)
    }
    fun clickAsset() = viewModelScope.launch{
        _memoState.emit(AddMemoState.SetAsset)
    }
    fun clickAttr() = viewModelScope.launch {
        _memoState.emit(AddMemoState.SetAttr)
    }

    fun clickSave() = viewModelScope.launch{
        _memoState.emit(AddMemoState.Save)
    }


}