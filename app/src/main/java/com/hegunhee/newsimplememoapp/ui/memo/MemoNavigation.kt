package com.hegunhee.newsimplememoapp.ui.memo

import com.hegunhee.newsimplememoapp.data.entity.Memo

sealed class MemoNavigation {

    object AddMemo : MemoNavigation()

    data class DetailMemo(val memo: Memo) : MemoNavigation()
}
