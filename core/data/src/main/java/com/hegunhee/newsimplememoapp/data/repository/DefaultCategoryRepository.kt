package com.hegunhee.newsimplememoapp.data.repository

import com.hegunhee.newsimplememoapp.data.dataSource.LocalDataSource
import com.hegunhee.newsimplememoapp.data.entity.CategoryEntity
import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class DefaultCategoryRepository @Inject constructor(
    private val localDataSource: LocalDataSource
): CategoryRepository {

    override suspend fun getAllCategoryByType(categoryType: CategoryType): List<String> {
        return localDataSource.getAllCategoryByType(categoryType.code).map { it.text }
    }

    // 현재 작성되어있는 카테고리가 존재하는 카테고리 이름인지 체크하는 로직
    override suspend fun checkIsCategory(categoryType: CategoryType, text: String): Boolean {
        return localDataSource.checkIsCategory(categoryType.code,text) != null
    }

    override suspend fun insertCategory(categoryType: CategoryType, text: String) {
        localDataSource.insertCategory(CategoryEntity(categoryType.code,text))
    }

    override suspend fun deleteCategory(text: String) {
        localDataSource.deleteCategory(text)
    }

}