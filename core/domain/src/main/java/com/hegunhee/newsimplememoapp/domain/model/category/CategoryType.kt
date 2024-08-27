package com.hegunhee.newsimplememoapp.domain.model.category

enum class CategoryType(val code : Int,val title : String,val subTitle : String) {
    EMPTY(code = -1,title = "",subTitle = ""),
    ASSET(0,title = "자산",subTitle = "자산"),
    ATTR_EXPENSE(1,title = "분류",subTitle = "지출/분류"),
    ATTR_INCOME(2,title = "분류",subTitle = "수입/분류")
}

fun Int.toCategoryType() : CategoryType {
    return when(this) {
        0 -> CategoryType.ASSET
        1 -> CategoryType.ATTR_EXPENSE
        2 -> CategoryType.ATTR_INCOME
        else -> CategoryType.EMPTY
    }
}