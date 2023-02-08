package com.hegunhee.newsimplememoapp.feature.detailStatics

sealed class BackButtonState {
    object Uninitialized : BackButtonState()
    object Back : BackButtonState()
}