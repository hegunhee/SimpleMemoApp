package com.hegunhee.newsimplememoapp.feature.detailMemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.GetMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.UpdateMemoUseCase
import com.hegunhee.newsimplememoapp.domain.model.MemoType
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

    private lateinit var memoEntity: MemoType.Memo

    private val _category : MutableStateFlow<String> = MutableStateFlow<String>("")
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
    val attr : StateFlow<String> = _attr.asStateFlow()

    val price = MutableLiveData<String>()
    val desc : MutableStateFlow<String> = MutableStateFlow<String>("")

    private val _memoState : MutableSharedFlow<DetailMemoState> = MutableSharedFlow<DetailMemoState>()
    val memoState : SharedFlow<DetailMemoState> = _memoState.asSharedFlow()

    fun initViewModel(memoId : Int) {
        viewModelScope.launch {
            this@DetailMemoViewModel.memoEntity = getMemoUseCase(memoId)
            initData()
        }
    }

    private fun initData() {
        initDay()
        initTime()
        memoEntity.let {
            _category.value = it.category
            _asset.value = it.asset
            _attr.value = it.attr
            price.value = it.price.toString()
            desc.value = it.description
        }
    }

    private fun initDay() {
        memoEntity.let {
            year = it.year
            month = it.month
            day = it.day
            dayOfWeek = it.dayOfWeek
        }

        _dateInfo.value = "${year}/${month}/${day} (${dayOfWeek}) "
    }

    private fun initTime() {
        memoEntity.let {
            ampm = it.amPm
            hour = it.hour
            minute = it.minute
        }
        _timeInfo.value = "$ampm ${hour}:${minute}"
    }

    fun setDate(year : Int,month : Int,day : Int) {
        this.year = year
        this.month = month
        this.day = day
        dayOfWeek = DateUtil.getDayOfWeek(year,month,day)

        _dateInfo.value = "${year}/${month}/${day} (${dayOfWeek})"
    }

    fun setTimeInfo() {
        _timeInfo.value = "$ampm ${hour}:${minute}"
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

    fun updateMemo() = viewModelScope.launch {
        memoEntity.copy(category.value,year,month,day,dayOfWeek,ampm,hour,minute,attr.value,price.value.toString().toInt(),asset.value,desc.value).also {
            updateMemoUseCase(it)
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
        }else{
            _memoState.emit(DetailMemoState.Update)
        }

    }

    fun clickRemove() = viewModelScope.launch{
        removeMemo()
        _memoState.emit(DetailMemoState.Remove)
    }

    private suspend fun removeMemo() {
        deleteMemoUseCase.invoke(memoEntity)
    }

    fun setAsset(asset : String) = viewModelScope.launch {
        _asset.value = asset
    }

    fun setAttr(attr : String) = viewModelScope.launch {
        _attr.value = attr
    }


}