package com.hegunhee.copose_memo
import com.hegunhee.newsimplememoapp.domain.model.TotalSum
import com.hegunhee.newsimplememoapp.domain.model.memo.MemoType

sealed interface MemoUiState {

    object Loading : MemoUiState

    data class Success(
        val year : Int,
        val month : Int,
        val memoTypeList : List<MemoType>,
        val totalSum : TotalSum
    ) : MemoUiState

    data class Error(
        val message : String
    ) : MemoUiState
}