package com.hegunhee.newsimplememoapp.feature.statics

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StaticsNavArgs(
    val attr : String,
    val year : Int,
    val month : Int
) : Parcelable