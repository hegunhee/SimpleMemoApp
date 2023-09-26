package com.hegunhee.newsimplememoapp.domain.model

sealed class CategoryType(val code : Int) {

    object Empty : CategoryType(-1)

    object Asset : CategoryType(0)

    object AttrExpenses : CategoryType(1)

    object AttrIncome : CategoryType(2)
}

fun Int.toCategoryType() : CategoryType{
    return when(this) {
        0 -> CategoryType.Asset
        1 -> CategoryType.AttrExpenses
        2 -> CategoryType.AttrIncome
        else -> CategoryType.Empty
    }
}