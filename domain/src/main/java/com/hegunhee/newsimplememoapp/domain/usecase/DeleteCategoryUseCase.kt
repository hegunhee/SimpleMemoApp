package com.hegunhee.newsimplememoapp.domain.usecase

import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(private val repository: MemoRepository){

    suspend operator fun invoke(text : String) {
        repository.deleteCategory(text)
    }
}