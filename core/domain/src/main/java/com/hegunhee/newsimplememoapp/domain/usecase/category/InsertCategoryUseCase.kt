package com.hegunhee.newsimplememoapp.domain.usecase.category

import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class InsertCategoryUsecase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(categoryType: CategoryType,name : String) : Result<String> {
        return categoryRepository.insertCategory(categoryType,name)
    }
}