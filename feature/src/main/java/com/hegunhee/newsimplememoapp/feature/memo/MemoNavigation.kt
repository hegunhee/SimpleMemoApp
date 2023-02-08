package com.hegunhee.newsimplememoapp.feature.memo

sealed class MemoNavigation {

    object AddMemo : MemoNavigation()

    data class DetailMemo(val memoId: Int) : MemoNavigation()
}
