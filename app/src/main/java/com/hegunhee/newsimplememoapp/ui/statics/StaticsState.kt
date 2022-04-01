package com.hegunhee.newsimplememoapp.ui.statics

sealed class StaticsState {
    object Uninitialized : StaticsState()

    data class Success(val list : List<StaticsData>) : StaticsState()

    object EmptyOrNull : StaticsState()
}