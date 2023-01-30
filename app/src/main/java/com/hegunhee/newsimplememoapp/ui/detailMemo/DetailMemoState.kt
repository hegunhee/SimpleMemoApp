package com.hegunhee.newsimplememoapp.ui.detailMemo


sealed class DetailMemoState{
    object Back : DetailMemoState()
    object SetDate : DetailMemoState()
    object SetTime : DetailMemoState()
    object SetAsset : DetailMemoState()
    object SetAttr : DetailMemoState()
    object Remove : DetailMemoState()
    object Save : DetailMemoState()
}
