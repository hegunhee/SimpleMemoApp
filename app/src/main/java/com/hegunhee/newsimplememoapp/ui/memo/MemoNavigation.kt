package com.hegunhee.newsimplememoapp.ui.memo

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity

sealed class MemoNavigation {

    object AddMemo : MemoNavigation()

    data class DetailMemo(val memoEntity: MemoEntity) : MemoNavigation()
}
