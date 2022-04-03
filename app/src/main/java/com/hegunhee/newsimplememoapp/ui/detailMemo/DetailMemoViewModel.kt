package com.hegunhee.newsimplememoapp.ui.detailMemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.data.entity.Memo
import com.hegunhee.newsimplememoapp.data.entity.changeKoreanDayOfWeek
import com.hegunhee.newsimplememoapp.data.entity.isExpenseAttr
import com.hegunhee.newsimplememoapp.data.entity.isIncomeAttr
import com.hegunhee.newsimplememoapp.domain.memoUsecase.InsertMemoUseCase
import com.hegunhee.newsimplememoapp.domain.memoUsecase.DeleteMemoUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class DetailMemoViewModel(
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val addMemoUseCase: InsertMemoUseCase
) : ViewModel() {

    private lateinit var memo: Memo

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

    val asset = MutableLiveData<String>()
    val attr = MutableLiveData<String>()

    val price = MutableLiveData<String>()
    val desc = MutableLiveData<String>()

    val memoState = MutableLiveData<DetailMemoState>(DetailMemoState.Uninitialized)

    fun initViewModel(memo: Memo) {
        this.memo = memo
        initData()
    }

    private fun initData() {
        initDay()
        initTime()
        memo.let {
            category.value = it.category
            asset.value = it.asset
            attr.value = it.attr
            price.value = it.price.toString()
            desc.value = it.description
        }
    }

    private fun initDay() {
        memo.let {
            year = it.year
            month = it.month
            day = it.day
            dayOfWeek = it.dayOfWeek
        }

        dateInfo.value = "${year}/${month}/${day} (${dayOfWeek}) "
    }

    private fun initTime() {
        memo.let {
            ampm = it.amPm
            hour = it.hour
            minute = it.minute
        }

        timeInfo.value = "$ampm ${hour}:${minute}"
    }

    fun setDate(date: LocalDate = LocalDate.now()) {
        date.let {
            year = it.year
            month = it.monthValue
            day = it.dayOfMonth
            dayOfWeek = changeKoreanDayOfWeek(it.dayOfWeek.toString())
        }

        dateInfo.value = "${year}/${month}/${day} (${dayOfWeek})"
    }

    fun setTimeInfo() {
        timeInfo.value = "$ampm ${hour}:${minute}"
    }

    fun setCategoryIncome() {
        category.value = "수입"
        if (isExpenseAttr(attr.value ?: return)) {
            attr.value = ""
        }
    }

    fun setCategoryExpense() {
        category.value = "지출"
        if (isIncomeAttr(attr.value ?: return)) {
            attr.value = ""
        }
    }

    fun saveData() = viewModelScope.launch {
        Memo(
            category.value!!,
            year,
            month,
            day,
            dayOfWeek,
            ampm,
            hour,
            minute,
            attr.value!!,
            price.value.toString().toInt(),
            asset.value!!,
            desc.value!!,
            memo.id
        ).apply { addMemoUseCase(this) }
    }


    fun removeMemo() = viewModelScope.launch {
        deleteMemoUseCase.invoke(memo)
    }

    fun back() {
        memoState.postValue(DetailMemoState.Back)
    }

    fun clickDate() {
        memoState.postValue(DetailMemoState.SetDate)
    }

    fun clickTime() {
        memoState.postValue(DetailMemoState.SetTime)
    }

    fun clickAsset() {
        memoState.postValue(DetailMemoState.SetAsset)
    }

    fun clickAttr() {
        memoState.postValue(DetailMemoState.SetAttr)
    }

    fun clickSave() {
        memoState.postValue(DetailMemoState.Save)
    }

    fun clickRemove() {
        removeMemo()
        memoState.postValue(DetailMemoState.Remove)
    }


}