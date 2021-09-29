package com.hegunhee.simplememoapp.data.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class accountItem(
    val category : String,
    val description : String?,
    val day : String,
    val time : String,
    val price : Int,
    val payType : String
) : Parcelable
