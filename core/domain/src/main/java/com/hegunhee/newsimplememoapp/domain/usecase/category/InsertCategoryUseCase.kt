package com.hegunhee.newsimplememoapp.domain.usecase.category

import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(private val repository: CategoryRepository) {

    suspend operator fun invoke(categoryType: CategoryType, text : String) : String {
        return repository.insertCategory(categoryType,text).getOrElse { "" }
    }
}