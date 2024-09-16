package com.hegunhee.newsimplememoapp.domain.usecase.category

import com.hegunhee.newsimplememoapp.domain.model.category.CategoryNamesByType
import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryNamesByTypeUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(categoryType: CategoryType) : Result<CategoryNamesByType> {
        return categoryRepository.findCategoryNamesByType(categoryType)
    }
}