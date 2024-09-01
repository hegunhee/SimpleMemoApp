package com.hegunhee.newsimplememoapp.domain.usecase.category

import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteServerCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(name : String) : Result<String> {
        return categoryRepository.deleteCategory(name)
    }
}