package com.hegunhee.newsimplememoapp.feature.common.category

interface CategoryActionHandler {

    fun onBottomSheetDismiss()

    fun onCategoryAdd(categoryType : CategoryType)

    fun onCategoryClick(type : CategoryType, category : String)
}