package com.hegunhee.newsimplememoapp.ui.detailStatics

sealed class BackButtonState {
    object Uninitialized : BackButtonState()
    object Back : BackButtonState()
}