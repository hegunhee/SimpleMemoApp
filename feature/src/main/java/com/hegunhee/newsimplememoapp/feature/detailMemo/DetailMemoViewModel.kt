package com.hegunhee.newsimplememoapp.feature.detailMemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.GetMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.UpdateMemoUseCase
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.feature.common.DateInfo
import com.hegunhee.newsimplememoapp.feature.common.TimeInfo
import com.hegunhee.newsimplememoapp.feature.common.isExpenseAttr
import com.hegunhee.newsimplememoapp.feature.common.isIncomeAttr
import com.hegunhee.newsimplememoapp.feature.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMemoViewModel @Inject constructor(
    private val getMemoUseCase : GetMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val updateMemoUseCase : UpdateMemoUseCase
) : ViewModel() {

    private val _memoEntity : MutableStateFlow<MemoType.Memo> = MutableStateFlow(MemoType.Memo.empty)
    val memoEntity : StateFlow<MemoType.Memo> = _memoEntity.asStateFlow()

    private val _category : MutableStateFlow<String> = MutableStateFlow<String>("")
    val category : StateFlow<String> = _category.asStateFlow()

    private val _dateInfo : MutableStateFlow<DateInfo> = MutableStateFlow<DateInfo>(DateInfo.emptyInfo)
    val dateInfo : StateFlow<DateInfo> = _dateInfo.asStateFlow()

    private val _timeInfo : MutableStateFlow<TimeInfo> = MutableStateFlow<TimeInfo>(TimeInfo.emptyInfo)
    val timeInfo : StateFlow<TimeInfo> = _timeInfo.asStateFlow()

    private val _asset : MutableStateFlow<String> = MutableStateFlow<String>("")
    val asset : StateFlow<String> = _asset.asStateFlow()

    private val _attr : MutableStateFlow<String> = MutableStateFlow<String>("")
    val attr : StateFlow<String> = _attr.asStateFlow()

    val price = MutableStateFlow<String>("")
    val desc : MutableStateFlow<String> = MutableStateFlow<String>("")

    private val _memoState : MutableSharedFlow<DetailMemoState> = MutableSharedFlow<DetailMemoState>()
    val memoState : SharedFlow<DetailMemoState> = _memoState.asSharedFlow()

    fun initViewModel(memoId : Int) {
        if(memoEntity.value != MemoType.Memo.empty) return
        viewModelScope.launch {
            _memoEntity.value = getMemoUseCase(memoId)
            initData()
        }
    }

    private fun initData() {
        memoEntity.value.run {
            val hourOfDay = if(amPm == "오후") {
                hour + 12
            } else {
                hour
            }
            setTime(hourOfDay,minute)
            setDate(year,month,day)
            _category.value = category
            _asset.value = asset
            _attr.value = attr
            this@DetailMemoViewModel.price.value = price.toString()
            desc.value = description
        }
    }

    fun setTime(
        hourOfDay : Int,
        minute : Int,
    ) {
        val (hour,amPm) = if(hourOfDay > 12) {
            Pair(hourOfDay-12,"오후")
        }else {
            Pair(hourOfDay,"오전")
        }
        _timeInfo.value = TimeInfo(hour,minute,amPm)
    }

    fun setDate(
        year : Int,
        month : Int,
        day : Int,
    ) {
        val dayOfWeek = DateUtil.getDayOfWeek(year,month,day)
        _dateInfo.value = DateInfo(year, month, day, dayOfWeek)
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

    private fun updateMemo() = viewModelScope.launch {
        val timeInfoValue = timeInfo.value
        val dateInfoValue = dateInfo.value
        memoEntity.value.copy(
            category = category.value,
            year = dateInfoValue.year,
            month = dateInfoValue.month,
            day = dateInfoValue.day,
            dayOfWeek = dateInfoValue.dayOfWeek,
            amPm = timeInfoValue.ampm,
            hour = timeInfoValue.hour,
            minute = timeInfoValue.minute,
            attr = attr.value,
            price = price.value.toInt(),
            asset = asset.value,
            description = desc.value
        ).let { memo ->
            updateMemoUseCase(memo)
        }
    }

    fun back() = viewModelScope.launch{
        _memoState.emit(DetailMemoState.Back)
    }

    fun clickDate() = viewModelScope.launch{
        _memoState.emit(DetailMemoState.SetDate)
    }

    fun clickTime() = viewModelScope.launch{
        _memoState.emit(DetailMemoState.SetTime)
    }

    fun clickAsset() = viewModelScope.launch{
        _memoState.emit(DetailMemoState.SetAsset)
    }

    fun clickAttr() = viewModelScope.launch{
        _memoState.emit(DetailMemoState.SetAttr)
    }

    fun clickUpdate() = viewModelScope.launch{
        if(asset.value.isBlank()){
            _memoState.emit(DetailMemoState.SetAsset)
        }else if(attr.value.isBlank()){
            _memoState.emit(DetailMemoState.SetAttr)
        }else if (price.value.isBlank()) {
            _memoState.emit(DetailMemoState.SetPrice)
        }else {
            updateMemo()
            _memoState.emit(DetailMemoState.Update)
        }
    }

    fun clickRemove() = viewModelScope.launch{
        removeMemo()
        _memoState.emit(DetailMemoState.Remove)
    }

    private suspend fun removeMemo() {
        deleteMemoUseCase(memoEntity.value)
    }

    fun setAsset(asset : String) = viewModelScope.launch {
        _asset.value = asset
    }

    fun setAttr(attr : String) = viewModelScope.launch {
        _attr.value = attr
    }
}