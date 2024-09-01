package com.hegunhee.newsimplememoapp.feature.detailMemo


sealed class DetailMemoState{
    object Remove : DetailMemoState()
    object Update : DetailMemoState()
    object SetPrice : DetailMemoState()
}
