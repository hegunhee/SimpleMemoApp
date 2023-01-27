package com.hegunhee.newsimplememoapp.ui.addMemo

sealed class AddMemoState {
    object Back : AddMemoState()
    object SetDate : AddMemoState()
    object SetTime : AddMemoState()
    object SetAsset : AddMemoState()
    object SetAttr : AddMemoState()
    object Save : AddMemoState()
}