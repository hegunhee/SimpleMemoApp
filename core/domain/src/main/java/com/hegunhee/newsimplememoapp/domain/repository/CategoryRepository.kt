package com.hegunhee.newsimplememoapp.domain.repository

import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType

interface CategoryRepository {

    suspend fun findAllCategoryByType(categoryType: CategoryType): List<String>

    suspend fun checkIsCategory(name: String): Boolean

    suspend fun insertCategory(categoryType: CategoryType, name: String) : String

    suspend fun deleteCategory(name: String) : String
}