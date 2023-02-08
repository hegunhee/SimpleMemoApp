package com.hegunhee.newsimplememoapp.feature.statics

sealed class StaticsState {
    object Uninitialized : StaticsState()

    data class Success(val list : List<StaticsData>) : StaticsState()

    object EmptyOrNull : StaticsState()
}