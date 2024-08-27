package com.hegunhee.newsimplememoapp.data.dataSource

import com.hegunhee.newsimplememoapp.data.api.CategoryApi
import com.hegunhee.newsimplememoapp.data.api.dto.category.CategoryEntity
import com.hegunhee.newsimplememoapp.data.api.dto.category.dto.CategoryNameResponse
import com.hegunhee.newsimplememoapp.data.api.dto.category.dto.CategoryNamesResponse
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import javax.inject.Inject

class DefaultRemoteDataSource @Inject constructor(
    private val categoryApi : CategoryApi
) : RemoteDataSource {
    override suspend fun findAllCategoryByType(categoryType: CategoryType): CategoryNamesResponse {
        return categoryApi.findAllBy(categoryType)
    }

    override suspend fun isExistCategory(categoryName: String): Boolean {
        return categoryApi.existsBy(categoryName)
    }

    override suspend fun insertCategory(categoryEntity: CategoryEntity): CategoryNameResponse {
        return categoryApi.save(categoryEntity)
    }

    override suspend fun deleteCategory(categoryName: String) : CategoryNameResponse{
        return categoryApi.delete(categoryName)
    }
}