package com.hegunhee.newsimplememoapp.data.repository

import com.hegunhee.newsimplememoapp.data.api.dto.category.CategoryEntity
import com.hegunhee.newsimplememoapp.data.dataSource.RemoteDataSource
import com.hegunhee.newsimplememoapp.data.mapper.toCategoriesByType
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryNamesByType
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    private val remoteDataSource : RemoteDataSource
): CategoryRepository {

    override suspend fun findCategoryNamesByType(categoryType: CategoryType): Result<CategoryNamesByType> {
        return runCatching {
            remoteDataSource.findAllCategoryByType(categoryType).toCategoriesByType()
        }
    }

    // 현재 작성되어있는 카테고리가 존재하는 카테고리 이름인지 체크하는 로직
    override suspend fun isExistCategory(name: String): Result<Boolean> {
        return runCatching{ remoteDataSource.isExistCategory(name) }
    }

    override suspend fun insertCategory(categoryType: CategoryType, name: String) : Result<String> {
        return runCatching { remoteDataSource.insertCategory(CategoryEntity(categoryType, name)).name }
    }

    override suspend fun deleteCategory(name: String) : Result<String> {
        return runCatching{ remoteDataSource.deleteCategory(name).name }
    }

}