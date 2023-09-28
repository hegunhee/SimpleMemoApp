package com.hegunhee.newsimplememoapp.feature.addMemo

sealed class AddMemoState {
    object Back : AddMemoState()
    object SetDate : AddMemoState()
    object SetTime : AddMemoState()
    object SetPrice : AddMemoState()
    object Save : AddMemoState()
}