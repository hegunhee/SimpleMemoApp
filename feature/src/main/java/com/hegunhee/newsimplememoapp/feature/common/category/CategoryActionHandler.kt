package com.hegunhee.newsimplememoapp.feature.common.category

import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType

interface CategoryActionHandler {

    fun onBottomSheetDismiss()

    fun onCategoryAdd(categoryType : CategoryType)

    fun onCategoryClick(type : CategoryType, category : String)
}