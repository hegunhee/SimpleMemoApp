package com.hegunhee.newsimplememoapp.ui.detailMemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.MemoEntity
import com.hegunhee.newsimplememoapp.data.entity.changeKoreanDayOfWeek
import com.hegunhee.newsimplememoapp.data.entity.isExpenseAttr
import com.hegunhee.newsimplememoapp.data.entity.isIncomeAttr
import com.hegunhee.newsimplememoapp.domain.memoUsecase.InsertMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.GetMemoUseCase
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailMemoViewModel @Inject constructor(
    private val getMemoUseCase : GetMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val addMemoUseCase: InsertMemoUseCase
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

    fun setDate(date: LocalDate = LocalDate.now()) {
        date.let {
            year = it.year
            month = it.monthValue
            day = it.dayOfMonth
            dayOfWeek = changeKoreanDayOfWeek(it.dayOfWeek.toString())
        }

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

    fun saveData() = viewModelScope.launch {
        MemoEntity(
            category.value,
            year,
            month,
            day,
            dayOfWeek,
            ampm,
            hour,
            minute,
            attr.value,
            price.value.toString().toInt(),
            asset.value,
            desc.value,
            memoEntity.id
        ).apply { addMemoUseCase(this) }
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

    fun clickSave() = viewModelScope.launch{
        if(asset.value.isBlank()){
            _memoState.emit(DetailMemoState.SetAsset)
        }else if(attr.value.isBlank()){
            _memoState.emit(DetailMemoState.SetAttr)
        }else{
            _memoState.emit(DetailMemoState.Save)
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