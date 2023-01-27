package com.hegunhee.newsimplememoapp.ui.addMemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.data.entity.changeKoreanDayOfWeek
import com.hegunhee.newsimplememoapp.data.entity.isExpenseAttr
import com.hegunhee.newsimplememoapp.data.entity.isIncomeAttr
import com.hegunhee.newsimplememoapp.domain.memoUsecase.InsertMemoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AddMemoViewModel @Inject constructor(
    private val addMemoUseCase : InsertMemoUseCase
) : ViewModel() {

    private val _category : MutableStateFlow<String> = MutableStateFlow<String>("지출")
    val category : StateFlow<String> = _category.asStateFlow()

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var dayOfWeek: String = ""
    private val _dateInfo : MutableStateFlow<String> = MutableStateFlow<String>("")
    val dateInfo : StateFlow<String> = _dateInfo.asStateFlow()

    var ampm: String = ""
    var hour: Int = 0
    var minute: Int = 0
    private val _timeInfo : MutableStateFlow<String> = MutableStateFlow<String>("")
    val timeInfo : StateFlow<String> = _timeInfo.asStateFlow()

    private val _asset : MutableStateFlow<String> = MutableStateFlow<String>("")
    val asset : StateFlow<String> = _asset.asStateFlow()

    private val _attr : MutableStateFlow<String> = MutableStateFlow<String>("")
    var attr : StateFlow<String> = _attr.asStateFlow()

    private val _memoState : MutableSharedFlow<AddMemoState> = MutableSharedFlow<AddMemoState>()
    val memoState : SharedFlow<AddMemoState> = _memoState.asSharedFlow()

    fun initData() {
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

    fun setDate(date: LocalDate = LocalDate.now(ZoneId.of("Asia/Seoul"))) {
        year = date.year
        month = date.monthValue
        day = date.dayOfMonth
        dayOfWeek = changeKoreanDayOfWeek(date.dayOfWeek.toString())
        _dateInfo.value = "${year}/${month}/${day} (${dayOfWeek})"
    }


    fun setTimeInfo(){
        _timeInfo.value = "$ampm ${hour}:${minute}"
    }

    fun setAsset(asset : String) {
        _asset.value = asset
    }

    fun setAttr(attr : String) {
        _attr.value = attr
    }

    fun setCategoryIncome() {
        _category.value = "수입"
        if(isExpenseAttr(attr.value)){
            _attr.value = ""
        }
    }

    fun setCategoryExpense() {
        _category.value = "지출"
        if(isIncomeAttr(attr.value)){
            _attr.value = ""
        }
    }


    fun saveData(price : Int, desc : String) = viewModelScope.launch {
        val memoEntity = MemoEntity(category.value,year,month,day,dayOfWeek,ampm,hour,minute,attr.value,price,asset.value,desc)
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
        if(asset.value.isBlank()){
            _memoState.emit(AddMemoState.SetAsset)
        }else if(attr.value.isBlank()){
            _memoState.emit(AddMemoState.SetAttr)
        }else {
            _memoState.emit(AddMemoState.Save)
        }
    }


}