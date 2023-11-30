package com.hegunhee.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.usecase.DeleteCategoryUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.GetAllCategoryByTypeUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.InsertCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategoryByTypeUseCase: GetAllCategoryByTypeUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel() {

    private val _uiState : MutableStateFlow<CategoryUiState> = MutableStateFlow(CategoryUiState.Loading)
    val uiState : StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun fetchCategoryList(categoryType: CategoryType) {
        viewModelScope.launch {
            val categoryList = getAllCategoryByTypeUseCase(categoryType)
            _uiState.value = CategoryUiState.Success(
                categoryType = categoryType,
                categoryList = categoryList
            )
        }
    }

    fun insertCategory(categoryType: CategoryType, category: String) {
        viewModelScope.launch {
            insertCategoryUseCase(categoryType, category)
            fetchCategoryList(categoryType)
        }
    }

    fun deleteCategory(categoryType : CategoryType,category : String) {
        viewModelScope.launch {
            deleteCategoryUseCase(category)
            fetchCategoryList(categoryType)
        }
    }
}