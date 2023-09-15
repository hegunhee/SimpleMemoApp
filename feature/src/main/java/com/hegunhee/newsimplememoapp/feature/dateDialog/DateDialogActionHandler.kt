package com.hegunhee.newsimplememoapp.feature.dateDialog

interface DateDialogActionHandler {

    fun onPreviousYearClick()

    fun onNextYearClick()

    fun onCurrentMonthClick()

    fun onMonthClick(month : Int)

    fun onDismissButtonClick()
}