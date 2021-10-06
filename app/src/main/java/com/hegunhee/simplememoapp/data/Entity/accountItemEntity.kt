package com.hegunhee.simplememoapp.data.Entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class accountItemEntity(
    val category: String,
    val day : String,
    val time : String,
    val asset : String,
    val attr : String,
    val price : Int,
    val description: String?,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
) :Parcelable