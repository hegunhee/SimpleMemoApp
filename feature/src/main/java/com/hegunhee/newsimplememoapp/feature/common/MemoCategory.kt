package com.hegunhee.newsimplememoapp.feature.common

sealed class MemoCategory(val text : String) {

    object Income : MemoCategory("수입")

    object Expenses : MemoCategory("지출")
}
