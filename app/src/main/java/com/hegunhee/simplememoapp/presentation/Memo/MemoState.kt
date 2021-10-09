package com.hegunhee.simplememoapp.presentation.Memo

import com.hegunhee.simplememoapp.data.Entity.accountItemEntity

sealed class MemoState{
    object Uninitalized : MemoState()

    object Loading : MemoState()

    data class Success(var ItemList : List<accountItemEntity>) : MemoState()
}
