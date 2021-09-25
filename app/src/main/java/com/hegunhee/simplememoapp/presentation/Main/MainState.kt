package com.hegunhee.simplememoapp.presentation.Main

sealed class MainState{
    object Uninitalized : MainState()

    object Loading : MainState()
}
