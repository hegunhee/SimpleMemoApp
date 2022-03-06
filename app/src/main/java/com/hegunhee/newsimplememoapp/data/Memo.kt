package com.hegunhee.newsimplememoapp.data

// Entity로 변환할때 주 생성자인 auto_increment가 추가되어야함
data class Memo(
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
