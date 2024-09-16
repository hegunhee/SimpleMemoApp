package com.hegunhee.statics

import com.hegunhee.newsimplememoapp.domain.model.memo.IncomeExpenseType
import com.hegunhee.newsimplememoapp.domain.model.memo.StaticsMemo

sealed interface StaticsUiState {

    object Loading : StaticsUiState

    data class Success(
        val year : Int,
        val month : Int,
        val incomeExpenseType : IncomeExpenseType,
        val staticsList : List<StaticsMemo>
    ) : StaticsUiState
}