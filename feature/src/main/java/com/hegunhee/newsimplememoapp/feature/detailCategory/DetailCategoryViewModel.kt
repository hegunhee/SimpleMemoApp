package com.hegunhee.newsimplememoapp.feature.detailCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.usecase.category.isExistCategoryUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.category.DeleteServerCategoryUseCase
import com.hegunhee.newsimplememoapp.domain.usecase.category.GetCategoryNamesByType
import com.hegunhee.newsimplememoapp.domain.usecase.category.InsertServerCategoryUsecase
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
    private val getCategoryNamesByType: GetCategoryNamesByType,
    private val insertServerCategoryUseCase : InsertServerCategoryUsecase,
    private val deleteServerCategoryUseCase: DeleteServerCategoryUseCase,
    private val isExistCategoryUseCase: isExistCategoryUseCase,
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

    fun initCategoryType(categoryType: CategoryType) {
        _categoryType.value = categoryType
        setCategoryList()
    }

    private fun setCategoryList() = viewModelScope.launch{
        getCategoryNamesByType(categoryType.value)
            .onSuccess { categoryNames ->
                _categoryList.value = categoryNames.names
            }.onFailure {

            }
    }

    override fun onCategoryRemoveClick(category: String) {
        viewModelScope.launch {
            deleteServerCategoryUseCase(category)
                .onSuccess {
                    _categoryNavigation.emit(DetailCategoryNavigation.Refresh)
                    setCategoryList()
                }.onFailure {

                }

        }
    }

    fun addCategory(category : String) {
        viewModelScope.launch {
            isExistCategoryUseCase(category)
                .onSuccess { isExist ->
                    if(isExist) {
                        return@launch
                    }
                    insertServerCategoryUseCase(categoryType.value,category)
                        .onSuccess {
                            _categoryNavigation.emit(DetailCategoryNavigation.Refresh)
                            setCategoryList()
                        }.onFailure {

                        }
                }.onFailure {

                }
        }
    }
}