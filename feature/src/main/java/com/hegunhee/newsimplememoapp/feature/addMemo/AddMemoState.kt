package com.hegunhee.newsimplememoapp.feature.addMemo

sealed class AddMemoState {
    object SetPrice : AddMemoState()
    object Save : AddMemoState()
}