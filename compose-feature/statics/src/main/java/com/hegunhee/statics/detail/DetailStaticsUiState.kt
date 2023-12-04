package com.hegunhee.statics.detail

import com.hegunhee.newsimplememoapp.domain.model.MemoType

sealed interface DetailStaticsUiState {

    object Loading : DetailStaticsUiState

    data class Success(
        val year : Int,
        val month : Int,
        val attr : String,
        val memoList : List<MemoType>,
        val totalPrice : String
    ) : DetailStaticsUiState
}