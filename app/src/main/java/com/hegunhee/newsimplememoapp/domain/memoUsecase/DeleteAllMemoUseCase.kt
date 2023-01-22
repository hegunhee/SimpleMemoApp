package com.hegunhee.newsimplememoapp.domain.memoUsecase

import com.hegunhee.newsimplememoapp.model.MemoRepository
import javax.inject.Inject


class DeleteAllMemoUseCase @Inject constructor(private val repository: MemoRepository) {
    suspend operator fun invoke(){
        repository.deleteAllMemo()
    }
}