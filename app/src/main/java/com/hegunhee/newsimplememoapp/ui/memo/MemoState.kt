package com.hegunhee.newsimplememoapp.ui.memo

import com.hegunhee.newsimplememoapp.data.entity.Memo

sealed class MemoState{
    object Uninitialized : MemoState()

    data class Success(var MemoList : List<Memo>) : MemoState()

    object EmptyOrNull : MemoState()
}
