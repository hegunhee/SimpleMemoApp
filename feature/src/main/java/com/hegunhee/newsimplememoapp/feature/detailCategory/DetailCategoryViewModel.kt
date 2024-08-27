package com.hegunhee.newsimplememoapp.feature.detailCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.usecase.category.DeleteCategoryUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.category.GetAllCategoryByTypeUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.category.InsertCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCategoryViewModel @Inject constructor(
    private val getAllCategoryByTypeUseCase: GetAllCategoryByTypeUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel(),DetailCategoryActionHandler {

    private val _categoryType : MutableStateFlow<CategoryType> = MutableStateFlow(CategoryType.EMPTY)
    val categoryType : StateFlow<CategoryType> = _categoryType.asStateFlow()

    private val _categoryList : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categoryList : StateFlow<List<String>> = _categoryList.asStateFlow()

    private val _categoryNavigation : MutableSharedFlow<DetailCategoryNavigation> = MutableSharedFlow()
    val categoryNavigation : SharedFlow<DetailCategoryNavigation> = _categoryNavigation.asSharedFlow()

    fun onBackButtonClick() {
        viewModelScope.launch {
            _categoryNavigation.emit(DetailCategoryNavigation.Back)
        }
    }

    fun setCategoryType(categoryType: CategoryType) {
        _categoryType.value = categoryType
        setCategoryList()
    }

    private fun setCategoryList() = viewModelScope.launch{
        _categoryList.value = getAllCategoryByTypeUseCase(categoryType.value)
    }

    override fun onCategoryRemoveClick(category: String) {
        viewModelScope.launch {
            deleteCategoryUseCase(category)
            _categoryNavigation.emit(DetailCategoryNavigation.Refresh)
            setCategoryList()
        }
    }

    fun addCategory(category : String) {
        viewModelScope.launch {
            insertCategoryUseCase(categoryType.value,category)
            _categoryNavigation.emit(DetailCategoryNavigation.Refresh)
            setCategoryList()
        }
    }


}