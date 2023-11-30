package com.hegunhee.category

import com.hegunhee.newsimplememoapp.domain.model.CategoryType

sealed interface CategoryUiState {

    object Loading : CategoryUiState

    data class Success(
        val categoryType : CategoryType,
        val categoryList : List<String>
    ) : CategoryUiState
}