package com.hegunhee.newsimplememoapp.domain.repository

import com.hegunhee.newsimplememoapp.domain.model.category.CategoryNamesByType
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType

interface CategoryRepository {

    suspend fun findCategoryNamesByType(categoryType: CategoryType): Result<CategoryNamesByType>

    suspend fun checkIsCategory(name: String): Boolean

    suspend fun insertCategory(categoryType: CategoryType, name: String) : String

    suspend fun deleteCategory(name: String) : String
}