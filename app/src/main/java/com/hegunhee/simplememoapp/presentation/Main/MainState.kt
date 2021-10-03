package com.hegunhee.simplememoapp.presentation.Main

import com.hegunhee.simplememoapp.data.Entity.accountItem

sealed class MainState{
    object Uninitalized : MainState()

    object Loading : MainState()

    data class Success(var ItemList : List<accountItem>) : MainState()
}
