package com.hegunhee.newsimplememoapp.feature.common

import android.icu.text.DecimalFormat

fun Int.toMoneyFormat() : String{
    return DecimalFormat("#,###").format(this)
}