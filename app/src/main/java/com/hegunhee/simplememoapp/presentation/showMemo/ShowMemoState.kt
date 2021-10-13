package com.hegunhee.simplememoapp.presentation.showMemo

import com.hegunhee.simplememoapp.data.Entity.accountItemEntity

sealed class ShowMemoState {
    object Uninitalized : ShowMemoState()

    object Loading : ShowMemoState()

    data class Succeed(var item : accountItemEntity) : ShowMemoState()
}