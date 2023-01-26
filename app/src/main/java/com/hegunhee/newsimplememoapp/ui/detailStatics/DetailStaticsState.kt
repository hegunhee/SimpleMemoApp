package com.hegunhee.newsimplememoapp.ui.detailStatics

import com.hegunhee.newsimplememoapp.data.entity.MemoEntity

sealed class DetailStaticsState {
    object Uninitialized : DetailStaticsState()

    class Success(val data : List<MemoEntity>) : DetailStaticsState()

    object NullOrEmpty : DetailStaticsState()
}