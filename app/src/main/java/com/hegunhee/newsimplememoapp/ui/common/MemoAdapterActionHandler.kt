package com.hegunhee.newsimplememoapp.ui.common

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity

interface MemoAdapterActionHandler {

    fun detailMemo(memoEntity : MemoEntity)
}