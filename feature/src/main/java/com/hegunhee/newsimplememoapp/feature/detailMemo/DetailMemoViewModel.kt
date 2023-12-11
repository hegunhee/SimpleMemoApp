package com.hegunhee.newsimplememoapp.feature.detailMemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.memo.DeleteMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.GetMemoUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.UpdateMemoUseCase
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.model.DateInfo
import com.hegunhee.newsimplememoapp.feature.common.MemoCategory
import com.hegunhee.newsimplememoapp.domain.model.TimeInfo
import com.hegunhee.newsimplememoapp.feature.common.category.CategoryActionHandler
import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.usecase.category.CheckIsCategoryUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.category.GetAllCategoryByTypeUseCase
import com.hegunhee.newsimplememoapp.feature.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMemoViewModel @Inject constructor(
    private val getMemoUseCase : GetMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val updateMemoUseCase : UpdateMemoUseCase,
    private val getAllCategoryByTypeUseCase: GetAllCategoryByTypeUseCase,
    private val checkIsCategoryUseCase: CheckIsCategoryUseCase
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

    val categoryList : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categoryType : MutableStateFlow<CategoryType> = MutableStateFlow(CategoryType.Empty)

    private val _detailCategoryNavigation : MutableSharedFlow<CategoryType> = MutableSharedFlow()
    val detailCategoryNavigation : SharedFlow<CategoryType> = _detailCategoryNavigation.asSharedFlow()

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
        viewModelScope.launch {
            if(checkIsCategoryUseCase(CategoryType.AttrExpenses,attr.value)) {
                _attr.value = ""
            }
        }
    }

    fun setCategoryExpense() {
        _memoCategory.value = MemoCategory.Expenses
        viewModelScope.launch {
            if (checkIsCategoryUseCase(CategoryType.AttrIncome,attr.value)) {
                _attr.value = ""
            }
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
            clickAsset()
        }else if(attr.value.isBlank()){
            clickAttr()
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
        deleteMemoUseCase(memoEntity.value.id)
    }

    fun clickAsset() = viewModelScope.launch {
        categoryList.value = getAllCategoryByTypeUseCase(CategoryType.Asset)
        categoryType.value = CategoryType.Asset
    }

    fun clickAttr() = viewModelScope.launch {
        when(memoCategory.value) {
            MemoCategory.Income -> {
                categoryList.value = getAllCategoryByTypeUseCase(CategoryType.AttrIncome)
                categoryType.value = CategoryType.AttrIncome
            }
            MemoCategory.Expenses -> {
                categoryList.value = getAllCategoryByTypeUseCase(CategoryType.AttrExpenses)
                categoryType.value = CategoryType.AttrExpenses
            }
        }
    }

    override fun onBottomSheetDismiss() {
        dismissBottomSheet()
    }

    override fun onCategoryAdd(categoryType: CategoryType) {
        viewModelScope.launch {
            _detailCategoryNavigation.emit(categoryType)
        }
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
        categoryList.value = emptyList()
        categoryType.value = CategoryType.Empty
    }

    fun refreshCategory() = viewModelScope.launch{
        if(categoryType.value !is CategoryType.Empty) {
            categoryList.value = getAllCategoryByTypeUseCase(categoryType.value)
        }
    }
}