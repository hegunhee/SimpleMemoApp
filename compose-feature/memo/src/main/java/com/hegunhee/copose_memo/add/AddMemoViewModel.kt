package com.hegunhee.copose_memo.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.util.DateUtil
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.DateInfo
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.model.TimeInfo
import com.hegunhee.newsimplememoapp.domain.model.isStandardMemo
import com.hegunhee.newsimplememoapp.domain.usecase.category.GetAllCategoryByTypeUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.memo.InsertMemoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMemoViewModel @Inject constructor(
    private val getAllCategoryByTypeUseCase: GetAllCategoryByTypeUseCase,
    private val insertMemoUseCase : InsertMemoUseCase
) : ViewModel(){
    
    private val _category : MutableStateFlow<String> = MutableStateFlow("지출")
    val category : StateFlow<String> = _category.asStateFlow()

    private val _dateInfo : MutableStateFlow<DateInfo> = MutableStateFlow(DateUtil.getTodayDate())
    val dateInfo : StateFlow<DateInfo> = _dateInfo.asStateFlow()

    private val _timeInfo : MutableStateFlow<TimeInfo> = MutableStateFlow(DateUtil.getTodayTime())
    val timeInfo : StateFlow<TimeInfo> = _timeInfo.asStateFlow()

    private val _subCategoryType : MutableStateFlow<CategoryType> = MutableStateFlow(CategoryType.Empty)
    val subCategoryType : StateFlow<CategoryType> = _subCategoryType.asStateFlow()

    private val _categoryList : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categoryList : StateFlow<List<String>> = _categoryList.asStateFlow()

    private val _asset : MutableStateFlow<String> = MutableStateFlow("")
    val asset : StateFlow<String> = _asset.asStateFlow()

    private val _attr : MutableStateFlow<String> = MutableStateFlow("")
    val attr : StateFlow<String> = _attr.asStateFlow()

    private val attrType : MutableStateFlow<CategoryType> = MutableStateFlow(CategoryType.AttrExpenses)

    private val _price : MutableStateFlow<String> = MutableStateFlow("")
    val price : StateFlow<String> = _price.asStateFlow()

    private val _description : MutableStateFlow<String> = MutableStateFlow("")
    val description : StateFlow<String> = _description.asStateFlow()

    fun setCategory(categoryName : String) {
        _category.value = categoryName
        if(categoryName == "지출" && attrType.value is CategoryType.AttrIncome) {
            _attr.value = ""
            attrType.value = CategoryType.AttrExpenses
        }else if(categoryName == "수입" && attrType.value is CategoryType.AttrExpenses) {
            _attr.value = ""
            attrType.value = CategoryType.AttrIncome
        }
    }

    fun onSelectDateClick(year : Int,month : Int,day : Int) {
        val dayOfWeek = DateUtil.getDayOfWeek(year,month,day)
        _dateInfo.value = DateInfo(year,month,day,dayOfWeek)
    }

    fun onSelectTimeClick(hour : Int,minute : Int,ampm : String) {
        _timeInfo.value = TimeInfo(hour,minute,ampm)
    }

    fun setCategoryType(categoryType : CategoryType) {
        viewModelScope.launch {
            _subCategoryType.value = categoryType
            _categoryList.value = getAllCategoryByTypeUseCase(categoryType)
        }
    }

    fun setSubCategoryItem(categoryType: CategoryType, categoryName : String) {
        when(categoryType) {
            is CategoryType.AttrExpenses, CategoryType.AttrIncome -> {
                attrType.value = categoryType
                _attr.value = categoryName
            }
            is CategoryType.Asset -> {
                _asset.value = categoryName
            }
            else -> { }
        }
        setCategoryType(CategoryType.Empty)
    }

    fun setPrice(price : String) {
        _price.value = price
    }

    fun setDescription(description : String) {
        _description.value = description
    }

    fun saveMemo() : Boolean {
        if (!isStandardMemo(category = category.value, asset = asset.value, attr = attr.value, price = price.value)) {
            return false
        }

        viewModelScope.launch {
            val timeInfoValue = timeInfo.value
            val dateInfoValue = dateInfo.value
            MemoType.Memo(
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
                description = description.value
            ).let {memo ->
                insertMemoUseCase(memo)
            }
        }
        return true
    }
}