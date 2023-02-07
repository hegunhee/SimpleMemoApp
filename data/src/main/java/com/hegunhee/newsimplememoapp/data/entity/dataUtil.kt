package com.hegunhee.newsimplememoapp.data.entity

fun isAsset(asset: String): Boolean = assetArray.contains(asset)

fun isIncomeAttr(attr: String): Boolean = incomeAttr.contains(attr)


fun isExpenseAttr(attr: String): Boolean = expenseAttr.contains(attr)

fun changeKoreanDayOfWeek(dayOfWeek: String): String =
    when (dayOfWeek) {
        "MONDAY" -> "월"
        "TUESDAY" -> "화"
        "WEDNESDAY" -> "수"
        "THURSDAY" -> "목"
        "FRIDAY" -> "금"
        "SATURDAY" -> "토"
        "SUNDAY" -> "일"
        else -> "error"
    }

val assetArray = arrayOf<String>("현금", "우리은행", "카카오뱅크")

val incomeAttr = arrayOf<String>("월급", "용돈", "기타")

val expenseAttr = arrayOf<String>("식비", "교통", "교육")
