package com.hegunhee.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.usecase.category.DeleteServerCategoryUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.category.GetCategoryNamesByType
import com.hegunhee.newsimplememoapp.domain.usecase.category.InsertServerCategoryUsecase
import com.hegunhee.newsimplememoapp.domain.usecase.category.isExistCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryNamesByType: GetCategoryNamesByType,
    private val insertServerCategoryUseCase : InsertServerCategoryUsecase,
    private val deleteServerCategoryUseCase: DeleteServerCategoryUseCase,
    private val isExistCategoryUseCase: isExistCategoryUseCase,
) : ViewModel() {

    private val _uiState : MutableStateFlow<CategoryUiState> = MutableStateFlow(CategoryUiState.Loading)
    val uiState : StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun fetchCategoryList(categoryType: CategoryType) {
        viewModelScope.launch {
            getCategoryNamesByType(categoryType)
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
            deleteServerCategoryUseCase(category)
                .onSuccess {
                    fetchCategoryList(categoryType)
                }.onFailure {

                }
        }
    }
}