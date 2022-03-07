package com.hegunhee.newsimplememoapp.data.Entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// Entity로 변환할때 주 생성자인 auto_increment가 추가되어야함
@Entity(tableName = "Memo")
@Parcelize
data class Memo(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val category: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeek: String,
    val amPm: String,
    val hour: Int,
    val minute: Int,
    val attr: String,
    val price: Int,
    val description: String
) : Parcelable{
}
