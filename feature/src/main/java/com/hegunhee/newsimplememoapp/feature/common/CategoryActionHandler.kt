package com.hegunhee.newsimplememoapp.feature.common

interface CategoryActionHandler {

    fun onBottomSheetDismiss()

    fun onCategoryAdd(categoryType : CategoryType)

    fun onCategoryClick(type : CategoryType,category : String)
}