package com.hegunhee.newsimplememoapp.ui.memo

sealed class MemoNavigation {

    object AddMemo : MemoNavigation()

    data class DetailMemo(val memoId: Int) : MemoNavigation()
}
