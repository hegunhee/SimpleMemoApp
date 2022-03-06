package com.hegunhee.newsimplememoapp.domain.memoTest

import com.hegunhee.newsimplememoapp.domain.UseCase
import com.hegunhee.newsimplememoapp.model.MemoRepository

class DeleteAllMemoUseCase(val repository: MemoRepository) : UseCase {
    suspend operator fun invoke(){
        repository.deleteAllMemo()
    }
}