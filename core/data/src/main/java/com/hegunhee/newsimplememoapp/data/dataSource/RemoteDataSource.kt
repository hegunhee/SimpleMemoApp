package com.hegunhee.newsimplememoapp.data.dataSource

import com.hegunhee.newsimplememoapp.data.api.dto.category.CategoryEntity
import com.hegunhee.newsimplememoapp.data.api.dto.category.dto.CategoryNameResponse
import com.hegunhee.newsimplememoapp.data.api.dto.category.dto.CategoryNamesResponse
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType


interface RemoteDataSource {

    suspend fun findAllCategoryByType(categoryType : CategoryType) : CategoryNamesResponse

    suspend fun isExistCategory(categoryName : String) : Boolean

    suspend fun insertCategory(categoryEntity : CategoryEntity) : CategoryNameResponse

    suspend fun deleteCategory(categoryName : String) : CategoryNameResponse
}