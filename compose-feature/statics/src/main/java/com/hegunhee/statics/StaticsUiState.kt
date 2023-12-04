package com.hegunhee.statics

import com.hegunhee.newsimplememoapp.domain.model.StaticsData

sealed interface StaticsUiState {

    object Loading : StaticsUiState

    data class Success(
        val year : Int,
        val month : Int,
        val category : String,
        val staticsList : List<StaticsData>
    ) : StaticsUiState
}