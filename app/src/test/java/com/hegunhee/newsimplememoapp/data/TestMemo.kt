package com.hegunhee.newsimplememoapp.data

import com.hegunhee.newsimplememoapp.data.Entity.Memo

data class TestMemo(
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
)

fun toTestMemo(memo: Memo): TestMemo {
    return TestMemo(
        memo.category,
        memo.year,
        memo.month,
        memo.day,
        memo.dayOfWeek,
        memo.amPm,
        memo.hour,
        memo.minute,
        memo.attr,
        memo.price,
        memo.description
    )
}