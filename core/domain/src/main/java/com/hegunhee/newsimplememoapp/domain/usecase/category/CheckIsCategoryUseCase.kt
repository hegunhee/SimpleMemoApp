package com.hegunhee.newsimplememoapp.domain.usecase.category

import com.hegunhee.newsimplememoapp.domain.model.CategoryType
import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class CheckIsCategoryUseCase @Inject constructor(private val repository: MemoRepository) {

    suspend operator fun invoke(categoryType: CategoryType,text : String) :Boolean{
        return repository.checkIsCategory(categoryType,text)
    }
}