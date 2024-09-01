package com.hegunhee.newsimplememoapp.domain.usecase.category

import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(private val repository: CategoryRepository){

    suspend operator fun invoke(text : String) : String {
        return repository.deleteCategory(text).getOrElse { "" }
    }
}