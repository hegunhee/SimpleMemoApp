package com.hegunhee.newsimplememoapp.feature.detailStatics

sealed class DetailStaticsNavigation() {

    object DateSelect : DetailStaticsNavigation()

    object Back : DetailStaticsNavigation()
}
