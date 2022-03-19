package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.model.MemoRepository

class DeleteAllMemoUseCase(private val repository: MemoRepository) : UseCase {
    suspend operator fun invoke(){
        repository.deleteAllMemo()
    }
}