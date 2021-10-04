package com.hegunhee.simplememoapp.data.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class accountItem(
    val category: String,
    val day : String,
    val time : String,
    val asset : String,
    val attr : String,
    val price : Int,
    val description: String?,
) : Parcelable
