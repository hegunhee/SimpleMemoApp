package com.hegunhee.newsimplememoapp.feature.dateDialog

sealed class DateDialogNavigation {

    object Dismiss : DateDialogNavigation()

    data class DisMissWithDate(val year : Int, val month : Int) : DateDialogNavigation()
}