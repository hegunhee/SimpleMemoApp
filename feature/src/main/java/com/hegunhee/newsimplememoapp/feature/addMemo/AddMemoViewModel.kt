package com.hegunhee.newsimplememoapp.feature.addMemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.usecase.memo.InsertMemoUseCase
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.feature.common.category.CategoryActionHandler
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.usecase.category.CheckIsCategoryUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.category.GetAllCategoryByTypeUseCase
import com.hegunhee.newsimplememoapp.domain.model.DateInfo
import com.hegunhee.newsimplememoapp.feature.common.MemoCategory
import com.hegunhee.newsimplememoapp.domain.model.TimeInfo
import com.hegunhee.newsimplememoapp.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMemoViewModel @Inject constructor(
    private val addMemoUseCase : InsertMemoUseCase,
    private val getAllCategoryByTypeUseCase: GetAllCategoryByTypeUseCase,
    private val checkIsCategoryUseCase: CheckIsCategoryUseCase
) : ViewModel(), CategoryActionHandler {

    private val _memoCategory: MutableStateFlow<MemoCategory> = MutableStateFlow<MemoCategory>(MemoCategory.Expenses)
    val memoCategory: StateFlow<MemoCategory> = _memoCategory.asStateFlow()

    private val _dateInfo: MutableStateFlow<DateInfo> = MutableStateFlow<DateInfo>(DateInfo.emptyInfo)
    val dateInfo: StateFlow<DateInfo> = _dateInfo.asStateFlow()

    private val _timeInfo: MutableStateFlow<TimeInfo> = MutableStateFlow<TimeInfo>(TimeInfo.emptyInfo)
    val timeInfo: StateFlow<TimeInfo> = _timeInfo.asStateFlow()

    private val _asset: MutableStateFlow<String> = MutableStateFlow<String>("")
    val asset: StateFlow<String> = _asset.asStateFlow()

    private val _attr: MutableStateFlow<String> = MutableStateFlow<String>("")
    val attr: StateFlow<String> = _attr.asStateFlow()

    val price: MutableStateFlow<String> = MutableStateFlow("")

    val description: MutableStateFlow<String> = MutableStateFlow("")

    private val _memoState: MutableSharedFlow<AddMemoState> = MutableSharedFlow<AddMemoState>()
    val memoState: SharedFlow<AddMemoState> = _memoState.asSharedFlow()

    val categoryList : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categoryType : MutableStateFlow<CategoryType> = MutableStateFlow(CategoryType.EMPTY)

    private val _detailCategoryNavigation : MutableSharedFlow<CategoryType> = MutableSharedFlow()
    val detailCategoryNavigation : SharedFlow<CategoryType> = _detailCategoryNavigation.asSharedFlow()

    init {
        setDate()
        setTime()
    }

    fun setTime(
        hourOfDay : Int = DateUtil.getHour(),
        minute : Int = DateUtil.getMinute()
    ) {
        val (hour,amPm) = if(hourOfDay > 12) {
            Pair(hourOfDay-12,"오후")
        }else {
            Pair(hourOfDay,"오전")
        }
        _timeInfo.value = TimeInfo(hour,minute,amPm)
    }

    fun setDate(
        year: Int = DateUtil.getYear(),
        month: Int = DateUtil.getMonth(),
        day: Int = DateUtil.getDayOfMonth(),
        dayOfWeek: String = DateUtil.getDayOfWeek(year, month, day)
    ) {
        _dateInfo.value = DateInfo(year = year, month = month, day = day, dayOfWeek = dayOfWeek)
    }

    fun setCategoryIncome() {
        _memoCategory.value = MemoCategory.Income
        viewModelScope.launch {
            if (checkIsCategoryUseCase(attr.value)) {
                _attr.value = ""
                dismissBottomSheet()
            }
        }
    }

    fun setCategoryExpense() {
        _memoCategory.value = MemoCategory.Expenses
        viewModelScope.launch {
            if (checkIsCategoryUseCase(attr.value)) {
                _attr.value = ""
                dismissBottomSheet()
            }
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
        categoryList.value = getAllCategoryByTypeUseCase(CategoryType.ASSET)
        categoryType.value = CategoryType.ASSET
    }

    fun clickAttr() = viewModelScope.launch {
        when(memoCategory.value) {
            MemoCategory.Income -> {
                categoryList.value = getAllCategoryByTypeUseCase(CategoryType.ATTR_INCOME)
                categoryType.value = CategoryType.ATTR_INCOME
            }
            MemoCategory.Expenses -> {
                categoryList.value = getAllCategoryByTypeUseCase(CategoryType.ATTR_EXPENSE)
                categoryType.value = CategoryType.ATTR_EXPENSE
            }
        }
    }

    fun onSaveButtonClick() = viewModelScope.launch {
        if (asset.value.isBlank()) {
            clickAsset()
        } else if (attr.value.isBlank()) {
            clickAttr()
        } else if (price.value.isBlank()) {
            _memoState.emit(AddMemoState.SetPrice)
        } else {
            saveData()
        }
    }

    private suspend fun saveData() {
        val timeInfoValue = timeInfo.value
        val dateInfoValue = dateInfo.value
        MemoType.Memo(
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
            description = description.value
        ).let {memo ->
            addMemoUseCase(memo)
        }
        _memoState.emit(AddMemoState.Save)
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
            CategoryType.EMPTY -> {}
            CategoryType.ASSET -> {
                _asset.value = category
            }
            CategoryType.ATTR_EXPENSE -> {
                _attr.value = category
            }
            CategoryType.ATTR_INCOME -> {
                _attr.value = category
            }
        }
        dismissBottomSheet()
    }

    private fun dismissBottomSheet() {
        categoryList.value = emptyList()
        categoryType.value = CategoryType.EMPTY
    }

    fun refreshCategory() = viewModelScope.launch{
        if(categoryType.value !=  CategoryType.EMPTY) {
            categoryList.value = getAllCategoryByTypeUseCase(categoryType.value)
        }
    }
}