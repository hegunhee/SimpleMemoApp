package com.hegunhee.copose_memo.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.compose_feature.util.DateUtil
import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.model.DateInfo
import com.hegunhee.newsimplememoapp.domain.model.TimeInfo
import com.hegunhee.newsimplememoapp.domain.usecase.GetAllCategoryByTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMemoViewModel @Inject constructor(
    private val getAllCategoryByTypeUseCase: GetAllCategoryByTypeUseCase,
) : ViewModel(){

    // 추후 하나의 UiState로 관리 예정
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
            _categoryList.value = listOf("식비","교통비","문화생활","건강","기타")
            // TODO 데이터 추가 후 getAllCategoryByTypeUseCase(categoryType)
        }
    }

    fun setSubCategoryItem(categoryType: CategoryType,categoryName : String) {
        when(categoryType) {
            is CategoryType.AttrExpenses, CategoryType.AttrIncome -> {
                _attr.value = categoryName
            }
            is CategoryType.Asset -> {
                _asset.value = categoryName
            }
            else -> { }
        }
        setCategoryType(CategoryType.Empty)
    }
}