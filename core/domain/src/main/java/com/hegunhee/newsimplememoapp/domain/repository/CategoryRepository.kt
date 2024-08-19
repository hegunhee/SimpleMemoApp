package com.hegunhee.newsimplememoapp.domain.repository

import com.hegunhee.newsimplememoapp.domain.model.CategoryType

interface CategoryRepository {

    suspend fun getAllCategoryByType(categoryType : CategoryType) : List<String>

    suspend fun checkIsCategory(categoryType: CategoryType, text : String) : Boolean

    suspend fun insertCategory(categoryType: CategoryType, text : String)

    suspend fun deleteCategory(text : String)
}