package com.hegunhee.simplememoapp.presentation.Main

import com.hegunhee.simplememoapp.data.Entity.accountItemEntity

sealed class MainState{
    object Uninitalized : MainState()

    object Loading : MainState()

    data class Success(var ItemList : List<accountItemEntity>) : MainState()
}
