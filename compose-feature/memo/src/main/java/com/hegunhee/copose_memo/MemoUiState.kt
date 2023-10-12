package com.hegunhee.copose_memo

import com.hegunhee.newsimplememoapp.domain.model.MemoType
import com.hegunhee.newsimplememoapp.domain.model.TotalPrice

sealed interface MemoUiState {

    object Loading : MemoUiState

    data class Success(
        val year : Int,
        val month : Int,
        val memoTypeList : List<MemoType>,
        val totalPrice : TotalPrice
    ) : MemoUiState

    data class Error(
        val message : String
    ) : MemoUiState
}