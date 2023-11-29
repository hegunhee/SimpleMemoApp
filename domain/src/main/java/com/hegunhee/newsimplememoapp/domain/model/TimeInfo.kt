package com.hegunhee.newsimplememoapp.domain.model

data class TimeInfo(
    val hour : Int,
    val minute : Int,
    val ampm : String,
    val timeStamp : String = "$ampm ${hour}:${minute}"
) {
    companion object {
        val emptyInfo = TimeInfo(0,0,"")
    }
}