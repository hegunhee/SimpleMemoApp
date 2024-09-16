package com.hegunhee.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.usecase.category.DeleteCategoryUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.category.GetCategoryNamesByTypeUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.category.InsertCategoryUsecase
import com.hegunhee.newsimplememoapp.domain.usecase.category.IsExistCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryNamesByTypeUseCase: GetCategoryNamesByTypeUseCase,
    private val insertServerCategoryUseCase : InsertCategoryUsecase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val isExistCategoryUseCase: IsExistCategoryUseCase,
) : ViewModel() {

    private val _uiState : MutableStateFlow<CategoryUiState> = MutableStateFlow(CategoryUiState.Loading)
    val uiState : StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun fetchCategoryList(categoryType: CategoryType) {
        viewModelScope.launch {
            getCategoryNamesByTypeUseCase(categoryType)
                .onSuccess { categoryNames ->
                    _uiState.value = CategoryUiState.Success(categoryType,categoryNames.names)
                }.onFailure {

                }
        }
    }

    fun insertCategory(categoryType: CategoryType, category: String) {
        viewModelScope.launch {
            isExistCategoryUseCase(category)
                .onSuccess { isExist ->
                    if(isExist) {
                        return@launch
                    }
                    insertServerCategoryUseCase(categoryType,category)
                        .onSuccess {
                            fetchCategoryList(categoryType)
                        }.onFailure {

                        }
                }.onFailure {

                }
        }
    }

    fun deleteCategory(categoryType : CategoryType, category : String) {
        viewModelScope.launch {
            deleteCategoryUseCase(category)
                .onSuccess {
                    fetchCategoryList(categoryType)
                }.onFailure {

                }
        }
    }
}