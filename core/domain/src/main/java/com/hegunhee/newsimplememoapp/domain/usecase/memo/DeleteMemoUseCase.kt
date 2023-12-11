package com.hegunhee.newsimplememoapp.domain.usecase.memo

import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject

class DeleteMemoUseCase @Inject constructor(private val repository: MemoRepository) {
    suspend operator fun invoke(id : Int){
        repository.deleteMemo(id)
    }
}