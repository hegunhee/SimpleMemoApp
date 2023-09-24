package com.hegunhee.newsimplememoapp.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "category")
@Parcelize
class CategoryEntity(
    val categoryCode : Int,
    @PrimaryKey val text : String
) : Parcelable
