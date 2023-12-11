package com.hegunhee.newsimplememoapp.domain.usecase.category

import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class GetAllCategoryByTypeUseCase @Inject constructor(private val repository: MemoRepository) {

    suspend operator fun invoke(categoryType: CategoryType) : List<String> {
        return repository.getAllCategoryByType(categoryType)
    }
}