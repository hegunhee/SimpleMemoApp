package com.hegunhee.newsimplememoapp.domain.usecase.category

import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class IsExistCategoryUseCase @Inject constructor(private val repository: CategoryRepository) {

    suspend operator fun invoke(name : String) : Result<Boolean>{
        return repository.isExistCategory(name)
    }
}