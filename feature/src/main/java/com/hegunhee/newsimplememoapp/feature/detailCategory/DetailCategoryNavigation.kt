package com.hegunhee.newsimplememoapp.feature.detailCategory

sealed class DetailCategoryNavigation {

    object Back : DetailCategoryNavigation()

    object Refresh : DetailCategoryNavigation()
}