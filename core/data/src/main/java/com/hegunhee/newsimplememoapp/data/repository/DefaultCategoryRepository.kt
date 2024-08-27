package com.hegunhee.newsimplememoapp.data.repository

import com.hegunhee.newsimplememoapp.data.api.dto.category.CategoryEntity
import com.hegunhee.newsimplememoapp.data.dataSource.RemoteDataSource
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    private val remoteDataSource : RemoteDataSource
): CategoryRepository {

    override suspend fun findAllCategoryByType(categoryType: CategoryType): List<String> {
        val response = remoteDataSource.findAllCategoryByType(categoryType)
        return response.names
    }

    // 현재 작성되어있는 카테고리가 존재하는 카테고리 이름인지 체크하는 로직
    override suspend fun checkIsCategory(name: String): Boolean {
        return remoteDataSource.isExistCategory(name)
    }

    override suspend fun insertCategory(categoryType: CategoryType, name: String) : String {
        return remoteDataSource.insertCategory(CategoryEntity(categoryType,name)).name
    }

    override suspend fun deleteCategory(name: String) : String {
        return remoteDataSource.deleteCategory(name).name
    }

}