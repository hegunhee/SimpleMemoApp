package com.hegunhee.newsimplememoapp.domain.usecase.category

import com.hegunhee.newsimplememoapp.domain.model.category.CategoryType
import com.hegunhee.newsimplememoapp.domain.repository.CategoryRepository
import javax.inject.Inject

class CheckIsCategoryUseCase @Inject constructor(private val repository: CategoryRepository) {

    suspend operator fun invoke(name : String) :Boolean{
        return repository.checkIsCategory(name)
    }
}