package com.hegunhee.newsimplememoapp.domain.usecase

import com.hegunhee.newsimplememoapp.domain.repository.MemoRepository
import javax.inject.Inject


class DeleteAllMemoUseCase @Inject constructor(private val repository: MemoRepository) {
    suspend operator fun invoke(){
        repository.deleteAllMemo()
    }
}