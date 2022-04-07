package com.hegunhee.newsimplememoapp.ui.detailStatics

import com.hegunhee.newsimplememoapp.data.entity.Memo

sealed class DetailStaticsState {
    object Uninitialized : DetailStaticsState()

    class Success(val data : List<Memo>) : DetailStaticsState()

    object NullOrEmpty : DetailStaticsState()
}