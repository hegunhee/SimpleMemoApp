package com.hegunhee.newsimplememoapp.feature.statics

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StaticsData(
    val percent: Int,
    val attr : String,
    val price : Int,
    val year : Int,
    val month : Int
) : Parcelable