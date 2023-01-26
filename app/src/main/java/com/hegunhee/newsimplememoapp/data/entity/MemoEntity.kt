package com.hegunhee.newsimplememoapp.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hegunhee.newsimplememoapp.domain.model.MemoType
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Memo")
@Parcelize
data class MemoEntity(
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
    val asset : String,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable {

    fun toMemo() : MemoType.Memo {
        return MemoType.Memo(category, year, month, day, dayOfWeek, amPm, hour, minute, attr, price, asset, description, id)
    }
}


fun MemoType.Memo.toMemoEntity() : MemoEntity{
    return MemoEntity(category, year, month, day, dayOfWeek, amPm, hour, minute, attr, price, asset, description, id)
}