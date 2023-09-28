package com.hegunhee.newsimplememoapp.feature.detailMemo


sealed class DetailMemoState{
    object Back : DetailMemoState()
    object SetDate : DetailMemoState()
    object SetTime : DetailMemoState()
    object SetPrice : DetailMemoState()
    object Remove : DetailMemoState()
    object Update : DetailMemoState()
}
