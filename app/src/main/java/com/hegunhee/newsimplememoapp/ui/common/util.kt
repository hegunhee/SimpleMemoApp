package com.hegunhee.newsimplememoapp.ui.common

import android.icu.text.DecimalFormat

fun Int.toMoneyFormat() : String{
    return DecimalFormat("#,###").format(this)
}