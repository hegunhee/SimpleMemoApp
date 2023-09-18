package com.hegunhee.newsimplememoapp.feature.common

data class DateInfo(
    val year : Int,
    val month : Int,
    val day : Int,
    val dayOfWeek : String,
    val dateStamp : String = "${year}/${month}/${day} (${dayOfWeek})"
) {
    companion object {
        val emptyInfo = DateInfo(0,0,0,"","")
    }
}