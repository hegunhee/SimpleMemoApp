package com.hegunhee.newsimplememoapp.feature.detailMemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.GetMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.UpdateMemoUseCase
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.feature.common.DateInfo
import com.hegunhee.newsimplememoapp.feature.common.MemoCategory
import com.hegunhee.newsimplememoapp.feature.common.TimeInfo
import com.hegunhee.newsimplememoapp.feature.common.assetArray
import com.hegunhee.newsimplememoapp.feature.common.category.CategoryActionHandler
import com.hegunhee.newsimplememoapp.feature.common.category.CategoryType
import com.hegunhee.newsimplememoapp.feature.common.expenseAttr
import com.hegunhee.newsimplememoapp.feature.common.incomeAttr
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
) : ViewModel(), CategoryActionHandler {

    private val _memoEntity : MutableStateFlow<MemoType.Memo> = MutableStateFlow(MemoType.Memo.empty)
    val memoEntity : StateFlow<MemoType.Memo> = _memoEntity.asStateFlow()

    private val _memoCategory : MutableStateFlow<MemoCategory> = MutableStateFlow<MemoCategory>(MemoCategory.Expenses)
    val memoCategory : StateFlow<MemoCategory> = _memoCategory.asStateFlow()

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

    val categoryHeaderText : MutableStateFlow<String> = MutableStateFlow("")
    val categoryList : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categoryType : MutableStateFlow<CategoryType> = MutableStateFlow(CategoryType.Empty)

    fun initViewModel(memoId : Int) {
        if(memoEntity.value != MemoType.Memo.empty) return
        viewModelScope.launch {
            _memoEntity.value = getMemoUseCase(memoId)
            initMemo()
        }
    }

    private fun initMemo() {
        memoEntity.value.run {
            val hourOfDay = if(amPm == "오후") {
                hour + 12
            } else {
                hour
            }
            setTime(hourOfDay,minute)
            setDate(year,month,day)
            _memoCategory.value = if(category == "지출") {
                MemoCategory.Expenses
            }else {
                MemoCategory.Income
            }
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
        _memoCategory.value = MemoCategory.Income
        if (isExpenseAttr(attr.value)) {
            _attr.value = ""
        }
    }

    fun setCategoryExpense() {
        _memoCategory.value = MemoCategory.Expenses
        if (isIncomeAttr(attr.value)) {
            _attr.value = ""
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

    private suspend fun updateMemo() {
        val timeInfoValue = timeInfo.value
        val dateInfoValue = dateInfo.value
        memoEntity.value.copy(
            category = memoCategory.value.text,
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

    fun clickRemove() = viewModelScope.launch{
        removeMemo()
        _memoState.emit(DetailMemoState.Remove)
    }

    private suspend fun removeMemo() {
        deleteMemoUseCase(memoEntity.value)
    }

    fun clickAsset() = viewModelScope.launch {
        categoryHeaderText.value = "자산"
        categoryList.value = assetArray.toList()
        categoryType.value = CategoryType.Asset
    }

    fun clickAttr() = viewModelScope.launch {
        categoryHeaderText.value = "분류"
        when(memoCategory.value) {
            MemoCategory.Income -> {
                categoryList.value = incomeAttr.toList()
                categoryType.value = CategoryType.AttrIncome
            }
            MemoCategory.Expenses -> {
                categoryList.value = expenseAttr.toList()
                categoryType.value = CategoryType.AttrExpenses
            }
        }
    }

    override fun onBottomSheetDismiss() {
        dismissBottomSheet()
    }

    override fun onCategoryAdd(categoryType: CategoryType) {

    }

    override fun onCategoryClick(type: CategoryType, category: String) {
        when(type) {
            CategoryType.Empty -> {}
            CategoryType.Asset -> {
                _asset.value = category
            }
            CategoryType.AttrExpenses -> {
                _attr.value = category
            }
            CategoryType.AttrIncome -> {
                _attr.value = category
            }
        }
        dismissBottomSheet()
    }

    private fun dismissBottomSheet() {
        categoryHeaderText.value = ""
        categoryList.value = emptyList()
        categoryType.value = CategoryType.Empty
    }
}