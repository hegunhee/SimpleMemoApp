package com.hegunhee.newsimplememoapp.domain.model

sealed class CategoryType(val code : Int,val title : String,val subTitle : String) {

    object Empty : CategoryType(code = -1,title = "",subTitle = "")

    object Asset : CategoryType(0,title = "자산",subTitle = "자산")

    object AttrExpenses : CategoryType(1,title = "분류",subTitle = "지출/분류")

    object AttrIncome : CategoryType(2,title = "분류",subTitle = "수입/분류")
}

fun Int.toCategoryType() : CategoryType{
    return when(this) {
        0 -> CategoryType.Asset
        1 -> CategoryType.AttrExpenses
        2 -> CategoryType.AttrIncome
        else -> CategoryType.Empty
    }
}