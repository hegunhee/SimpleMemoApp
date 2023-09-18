package com.hegunhee.newsimplememoapp.feature.addMemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.InsertMemoUseCase
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.feature.common.TimeInfo
import com.hegunhee.newsimplememoapp.feature.common.isExpenseAttr
import com.hegunhee.newsimplememoapp.feature.common.isIncomeAttr
import com.hegunhee.newsimplememoapp.feature.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMemoViewModel @Inject constructor(
    private val addMemoUseCase : InsertMemoUseCase
) : ViewModel() {

    private val _category: MutableStateFlow<String> = MutableStateFlow<String>("지출")
    val category: StateFlow<String> = _category.asStateFlow()

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var dayOfWeek: String = ""
    private val _dateInfo: MutableStateFlow<String> = MutableStateFlow<String>("")
    val dateInfo: StateFlow<String> = _dateInfo.asStateFlow()

    private val _timeInfo: MutableStateFlow<TimeInfo> = MutableStateFlow<TimeInfo>(TimeInfo.emptyInfo)
    val timeInfo: StateFlow<TimeInfo> = _timeInfo.asStateFlow()

    private val _asset: MutableStateFlow<String> = MutableStateFlow<String>("")
    val asset: StateFlow<String> = _asset.asStateFlow()

    private val _attr: MutableStateFlow<String> = MutableStateFlow<String>("")
    var attr: StateFlow<String> = _attr.asStateFlow()

    val price: MutableStateFlow<String> = MutableStateFlow("")

    val description: MutableStateFlow<String> = MutableStateFlow("")

    private val _memoState: MutableSharedFlow<AddMemoState> = MutableSharedFlow<AddMemoState>()
    val memoState: SharedFlow<AddMemoState> = _memoState.asSharedFlow()

    fun initData() {
        setDate()
        setTime()
    }

    fun setTime(
        hour : Int = DateUtil.getHour(),
        minute : Int = DateUtil.getMinute()
    ) {
        _timeInfo.value = if(hour > 12) {
            TimeInfo(hour-12,minute,"오후")
        }else {
            TimeInfo(hour,minute,"오전")
        }
    }

    fun setDate(
        year: Int = DateUtil.getYear(),
        month: Int = DateUtil.getMonth(),
        day: Int = DateUtil.getDayOfMonth(),
        dayOfWeek: String = DateUtil.getDayOfWeek(year, month, day)
    ) {
        this.year = year
        this.month = month
        this.day = day
        this.dayOfWeek = dayOfWeek
        _dateInfo.value = "${year}/${month}/${day} (${dayOfWeek})"
    }

    fun setAsset(asset: String) {
        _asset.value = asset
    }

    fun setAttr(attr: String) {
        _attr.value = attr
    }

    fun setCategoryIncome() {
        _category.value = "수입"
        if (isExpenseAttr(attr.value)) {
            _attr.value = ""
        }
    }

    fun setCategoryExpense() {
        _category.value = "지출"
        if (isIncomeAttr(attr.value)) {
            _attr.value = ""
        }
    }

    fun back() = viewModelScope.launch {
        _memoState.emit(AddMemoState.Back)
    }

    fun clickDate() = viewModelScope.launch {
        _memoState.emit(AddMemoState.SetDate)
    }

    fun clickTime() = viewModelScope.launch {
        _memoState.emit(AddMemoState.SetTime)
    }

    fun clickAsset() = viewModelScope.launch {
        _memoState.emit(AddMemoState.SetAsset)
    }

    fun clickAttr() = viewModelScope.launch {
        _memoState.emit(AddMemoState.SetAttr)
    }

    fun onSaveButtonClick() = viewModelScope.launch {
        if (asset.value.isBlank()) {
            _memoState.emit(AddMemoState.SetAsset)
        } else if (attr.value.isBlank()) {
            _memoState.emit(AddMemoState.SetAttr)
        } else if (price.value.isBlank()) {
            _memoState.emit(AddMemoState.SetPrice)
        } else {
            saveData()
        }
    }

    private suspend fun saveData() {
        val timeInfoValue = timeInfo.value
        val memo = MemoType.Memo(category.value,year,month,day,dayOfWeek,timeInfoValue.ampm,timeInfoValue.hour,timeInfoValue.minute,attr.value,price.value.toInt(),asset.value,description.value)
        addMemoUseCase(memo)
        _memoState.emit(AddMemoState.Save)
    }
}