package com.hegunhee.newsimplememoapp.feature.detailStatics

import com.hegunhee.newsimplememoapp.domain.model.MemoType


sealed class DetailStaticsState {
    object Uninitialized : DetailStaticsState()

    class Success(val data : List<MemoType>) : DetailStaticsState()

    object NullOrEmpty : DetailStaticsState()
}