package com.hegunhee.newsimplememoapp.feature.statics

sealed class StaticsNavigation {

    object SelectDate : StaticsNavigation()

    data class Detail(val navArgs : StaticsNavArgs) : StaticsNavigation()
}